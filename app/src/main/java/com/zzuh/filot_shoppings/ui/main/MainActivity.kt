/*
* 만들어야 할 fragment
* cart fragment -> viewModel
* product Detail Activity
*/

package com.zzuh.filot_shoppings.ui.main

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
import com.zzuh.filot_shoppings.ui.main.viewmodel.CategoryViewModel
import android.widget.LinearLayout

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.zzuh.filot_shoppings.ui.login.LoginActivity
import com.zzuh.filot_shoppings.ui.main.viewmodel.MainViewModel

const val BANNER_IMG_URL = "https://file.cafe24cos.com/banner-admin-live/upload/joker8992/ede80c3b-076d-40e9-83c6-fb4c1f12c00b.jpeg"

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding

    var list = listOf("test1", "test2", "test3")
    var tabList = mutableListOf<TabLayout.Tab>()

    lateinit var mainFragment: MainFragment
    lateinit var mainViewModel: MainViewModel

    lateinit var cartFragment: CartFragment

    lateinit var categoryFragment: CategoryFragment
    lateinit var categoryViewModel: CategoryViewModel

    lateinit var transaction: FragmentTransaction
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()

        categoryViewModel = CategoryViewModel()
        mainViewModel = MainViewModel()

        categoryFragment = CategoryFragment(categoryViewModel)
        mainFragment = MainFragment(categoryViewModel)
        cartFragment = CartFragment()

        Glide.with(this)
            .load(BANNER_IMG_URL)
            .into(binding.bannerImg)

        setContentView(binding.root)

        initToolBarSetting()
        initFragmentSetting()
        initViewModelSetting()

        binding.drawerLayout.needLoginTv.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
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

        for(item in list){
            tabList.add(binding.mainTabLayout.newTab())
            tabList.last().text = item
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
                if(tab != null){
                    transaction = fragmentManager.beginTransaction()
                    categoryViewModel.setCategoryName(tab.text as String)
                    transaction.replace(R.id.fragment_content, categoryFragment)
                    if(mainViewModel.isMain!!) mainViewModel.isMain = false
                    //transaction.addToBackStack(tab.text as String)
                    transaction.commit()
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("Tester","onTabReselected")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("Tester","onTabUnselected")
            }
        })
    }
    private fun initViewModelSetting(){
        mainViewModel._isMain.observe(this, Observer { binding.bannerImg.visibility = if (mainViewModel.isMain!!) View.VISIBLE else View.GONE })
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
            transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_content, cartFragment)
            if(mainViewModel.isMain!!) mainViewModel.isMain = false
            transaction.commit()
        }
        return super.onOptionsItemSelected(item)
    }
}