package com.zzuh.filot_shoppings_admin.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.databinding.ActivityCategoryManageBinding
import com.zzuh.filot_shoppings_admin.ui.adapter.MainCategoryAdapter
import com.zzuh.filot_shoppings_admin.ui.viewmodel.CategoryManageViewModel
import com.zzuh.filot_shoppings_admin.ui.viewmodel.CategoryManageViewModelFactory

class CategoryManageActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryManageBinding
    lateinit var categoryManageViewModel: CategoryManageViewModel
    lateinit var mainCategoryAdapter: MainCategoryAdapter
    val token: String by lazy {
        val intent = Intent()
        intent.getStringExtra("token").toString()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryManageBinding.inflate(layoutInflater)
        categoryManageViewModel = ViewModelProvider(this, CategoryManageViewModelFactory(token))
            .get(CategoryManageViewModel::class.java)
        setContentView(binding.root)
        binding.mainCategoryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainCategoryAdapter = MainCategoryAdapter(emptyList())
        binding.mainCategoryRecyclerView.adapter = mainCategoryAdapter
        viewModelSetting()
    }
    private fun viewModelSetting(){
        categoryManageViewModel.categoryList.observe(this, Observer {
            if(it.isEmpty()){
                binding.loadingProgressBar.visibility = View.GONE
                binding.newCategoryLayout.visibility = View.VISIBLE
                Toast.makeText(this,"등록된 카테고리가 없습니다.", Toast.LENGTH_SHORT).show()
                return@Observer
            }
            mainCategoryAdapter.updateData(it)
        })
        categoryManageViewModel.networkState.observe(this, Observer {
            when(it){
                NetworkState.LOADING -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                    binding.loadedLayout.visibility = View.GONE
                }
                NetworkState.LOADED -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.loadedLayout.visibility = View.VISIBLE
                }
                NetworkState.ERROR -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.loadedLayout.visibility = View.GONE
                }
            }
        })
    }
}