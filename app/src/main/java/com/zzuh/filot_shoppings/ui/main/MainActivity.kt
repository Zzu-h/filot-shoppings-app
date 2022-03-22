/*
* 만들어야 할 fragment
* cart fragment -> viewModel
* product Detail Activity
*/

package com.zzuh.filot_shoppings.ui.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.zzuh.filot_shoppings.R
import com.zzuh.filot_shoppings.databinding.ActivityMainBinding
import android.widget.LinearLayout
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.vo.Category
import com.zzuh.filot_shoppings.ui.main.adapter.DrawerCategoryAdapter
import com.zzuh.filot_shoppings.ui.main.viewmodel.*
import com.zzuh.filot_shoppings.ui.user.viewmodel.UserInfoViewModel
import com.zzuh.filot_shoppings.ui.user.viewmodel.UserInfoViewModelFactory
import com.zzuh.filot_shoppings_admin.ui.admin.AdminActivity
import com.zzuh.filot_shoppings_login.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding

    var mainCategoryList = emptyList<Category>()
    var tabList = mutableListOf<TabLayout.Tab>()
    val BANNER_IMG_URL by lazy { intent.getStringExtra("banner") }

    lateinit var mainFragment: MainFragment
    lateinit var cartFragment: CartFragment
    lateinit var categoryFragment: CategoryFragment

    lateinit var productListViewModel: ProductListViewModel
    lateinit var categoryViewModel: CategoryViewModel
    lateinit var userInfoViewModel: UserInfoViewModel

    lateinit var transaction: FragmentTransaction
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()

        productListViewModel = ViewModelProvider(this, ProductListViewModelFactory(this))
            .get(ProductListViewModel::class.java)
        categoryViewModel = ViewModelProvider(this, CategoryViewModelFactory(this))
            .get(CategoryViewModel::class.java)

        categoryFragment = CategoryFragment(productListViewModel, categoryViewModel)
        mainFragment = MainFragment(productListViewModel)
        cartFragment = CartFragment()

        Glide.with(this)
            .load(BANNER_IMG_URL)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(binding.bannerImg)

        autoLogin()
        setContentView(binding.root)

        initToolBarSetting()
        initViewModelSetting()
        binding.drawerLayout.needLoginTv.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.headerTitle.setOnClickListener {
            changeFragment(true)
        }
    }
    override fun onResume() {
        super.onResume()
        // room data에서 token을 받아와 유저 정보를 요청한다.
        binding.drawer.closeDrawers()
        Log.d("Tester", "onResume")
        autoLogin()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_header, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        if(item.itemId == R.id.menu_cart){
            changeFragment(false,"cart")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun autoLogin(){
        CoroutineScope(Dispatchers.Main).launch {
            val spToken = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
            val token = spToken.getString("token", null) ?: return@launch
            val email = spToken.getString("email", null) ?: return@launch

            userInfoViewModel = ViewModelProvider(this@MainActivity, UserInfoViewModelFactory(email, token))
                .get(UserInfoViewModel::class.java)
            userInfoViewModel.userInfo.observe(this@MainActivity, Observer {
                binding.drawerLayout.needLoginLayout.visibility = View.GONE
                binding.drawerLayout.userNameTv.text = "${it.name}님, 안녕하세요!"
                binding.drawerLayout.userNameTv.visibility = View.VISIBLE
                binding.drawerLayout.profileIconIv.visibility = View.VISIBLE
                binding.drawerLayout.editUserInfoLayout.visibility = View.VISIBLE
                binding.drawerLayout.logoutLayout.visibility = View.VISIBLE
                binding.drawerLayout.logoutDivider.visibility = View.VISIBLE
                if(it.roles.contains("ROLE_ADMIN")) {
                    binding.drawerLayout.adminPageLayout.visibility = View.VISIBLE
                    binding.drawerLayout.adminPageLayout.setOnClickListener {
                        val intent = Intent(this@MainActivity, AdminActivity::class.java)
                        startActivity(intent)
                    }
                }
            })
            binding.drawerLayout.logoutLayout.setOnClickListener{
                //do Logout
                this@MainActivity.viewModelStore.clear()
                val edit = spToken.edit()
                edit.remove("token")
                edit.remove("email")
                edit.apply()
                finish()
                startActivity(intent)
            }
        }
    }
    private fun initToolBarSetting():Unit{
        setSupportActionBar(binding.headerToolbar)

        toggle = ActionBarDrawerToggle(this, binding.drawer,0,0)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toggle.syncState()

        binding.drawerLayout.backBtn.setOnClickListener { binding.root.closeDrawer(GravityCompat.START) }
    }
    private fun initFragmentSetting():Unit{
        /*
        다음을 정리한다
        1. 처음은 main 화면을 띄운다.
        백에서 카테고리 종류를 받아온다.
        이를 통해 탭 레이아웃을 생성한다.
        탭 레이아웃을 클릭하면 백으로 해당 탭 정보를 담아서 보낸다.
        카테고리 프래그먼트는 백에서 데이터를 로딩한다. --> 이때 로딩화면
        --> 룩핀 참고
        이유: 프래그먼트 클래스 하나 당 하나의 프래그먼트만 생성할 수 있음
        */

        for(item in mainCategoryList){
            tabList.add(binding.mainTabLayout.newTab())
            tabList.last().text = item.name
            binding.mainTabLayout.addTab(tabList.last())
        }
        val root: View = binding.mainTabLayout.getChildAt(0)
        if (root is LinearLayout) {
            (root as LinearLayout).showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            val drawable = GradientDrawable()
            drawable.setColor(resources.getColor(R.color.grey))
            drawable.setSize(1, 1)
            (root as LinearLayout).dividerPadding = 3
            (root as LinearLayout).dividerDrawable = drawable
        }

        transaction.add(R.id.fragment_content, mainFragment)
        transaction.commit()
        binding.mainTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let{ changeFragment(false, tab.text as String) }
                categoryViewModel.isMainCategory = true
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("Tester","onTabReselected")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("Tester","onTabUnselected")
            }
        })
        binding.drawerLayout.cartLayout.setOnClickListener {
            binding.drawer.closeDrawers()
            changeFragment(false, "cart")
        }
    }
    private fun initViewModelSetting(){
        categoryViewModel._isMain.observe(this, Observer {
            binding.bannerImg.visibility = if (categoryViewModel.isMain!!) View.VISIBLE else View.GONE
        })
        categoryViewModel.mainCategoryList.observe(this, Observer {
            this.mainCategoryList = it
            initFragmentSetting()
            initDrawerLayoutCategorySetting()
        })
        categoryViewModel.getMainCategoryList("main")
        categoryViewModel.mainCategoryNetworkState.observe(this, Observer {
            when(it){
                NetworkState.LOADING -> {
                    binding.loadingBar.visibility = View.VISIBLE
                    binding.bannerImg.visibility = View.GONE
                    binding.drawer.visibility = View.GONE
                    binding.mainTabLayout.visibility = View.GONE
                    binding.fragmentContent.visibility = View.GONE
                    binding.txtError.visibility = View.GONE
                }
                NetworkState.LOADED -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.bannerImg.visibility = View.VISIBLE
                    binding.drawer.visibility = View.VISIBLE
                    binding.mainTabLayout.visibility = View.VISIBLE
                    binding.fragmentContent.visibility = View.VISIBLE
                    binding.txtError.visibility = View.GONE
                }
                NetworkState.ERROR -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.bannerImg.visibility = View.GONE
                    binding.drawer.visibility = View.GONE
                    binding.mainTabLayout.visibility = View.GONE
                    binding.fragmentContent.visibility = View.GONE
                    binding.txtError.visibility = View.VISIBLE
                }
            }
        })
    }
    private fun initDrawerLayoutCategorySetting(){
        val adapter = DrawerCategoryAdapter(mainCategoryList, object: DrawerCategoryAdapter.OnChangeCategory{
            override fun changeFragment(name: String) {
                binding.drawer.closeDrawers()
                for(item in tabList) {
                    if(item.text == name) {
                        binding.mainTabLayout.selectTab(item)
                        break
                    }
                }
            }
        }, )

        var gridLayout = GridLayoutManager(this, 2)
        binding.drawerLayout.categoryRv.layoutManager = gridLayout
        binding.drawerLayout.categoryRv.adapter = adapter
    }
    private fun changeFragment(isMain: Boolean, name: String = "main"){
        transaction = fragmentManager.beginTransaction()
        if(isMain)
            transaction.replace(R.id.fragment_content, mainFragment)
        else if(name == "cart")
            transaction.replace(R.id.fragment_content, cartFragment)
        else{
            productListViewModel.setCategoryName(name)
            transaction.replace(R.id.fragment_content, categoryFragment)
        }
        categoryViewModel.isMain = isMain
        transaction.commit()
    }
}