package com.zzuh.filot_shoppings.ui.main

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.zzuh.filot_shoppings.R
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.vo.Category
import com.zzuh.filot_shoppings.databinding.FragmentCategoryBinding
import com.zzuh.filot_shoppings.ui.main.adapter.ProductListAdapter
import com.zzuh.filot_shoppings.ui.main.viewmodel.CategoryViewModel
import com.zzuh.filot_shoppings.ui.main.viewmodel.ProductListViewModel

class CategoryFragment(
    private val productListViewModel: ProductListViewModel,
    private val categoryViewModel: CategoryViewModel,
) : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    lateinit var adapter: ProductListAdapter

    var subCategoryList = emptyList<Category>()
    var subTabList = mutableListOf<TabLayout.Tab>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        adapter = ProductListAdapter(emptyList(), requireContext())

        var gridLayout = GridLayoutManager(context, 2)
        binding.productListRecyclerView.layoutManager = gridLayout
        binding.productListRecyclerView.adapter = adapter

        productListViewModel.productList.observe(this, Observer{
            if(it == null || it.products.isEmpty())
                return@Observer

            adapter.updateData(it.products)
            adapter.notifyDataSetChanged()
        })
        productListViewModel.categoryName.observe(this, Observer {
            binding.categoryTitleTextView.text = it
            productListViewModel.getProductList(it)
            categoryViewModel.getSubCategoryList(it)
        })
        categoryViewModel.subCategoryList.observe(this, Observer {
            subCategoryList = it
            binding.subTabLayout.removeAllTabs()
            subTabList.clear()
            for(item in subCategoryList){
                subTabList.add(binding.subTabLayout.newTab())
                subTabList.last().text = item.name
                binding.subTabLayout.addTab(subTabList.last())
            }
            val root: View = binding.subTabLayout.getChildAt(0)
            if (root is LinearLayout) {
                (root as LinearLayout).showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
                val drawable = GradientDrawable()
                drawable.setColor(resources.getColor(R.color.grey))
                drawable.setSize(1, 1)
                (root as LinearLayout).dividerPadding = 3
                (root as LinearLayout).dividerDrawable = drawable
            }
        })

        productListViewModel.networkState.observe(this, Observer {
            if(it == NetworkState.ERROR || it == NetworkState.LOADING) viewControl(it)
            else if(categoryViewModel.subCategoryNetworkState.value == NetworkState.LOADED) viewControl(it)
        })
        categoryViewModel.subCategoryNetworkState.observe(this, Observer {
            if(it == NetworkState.ERROR || it == NetworkState.LOADING) viewControl(it)
            else if(productListViewModel.networkState.value == NetworkState.LOADED) viewControl(it)
        })
    }
    private fun viewControl(it: NetworkState){
        when(it){
            NetworkState.LOADING -> {
                binding.loadingBar.visibility = View.VISIBLE
                binding.subTabLayout.visibility = View.GONE
                binding.productListRecyclerView.visibility = View.GONE
                binding.noDataTv.visibility = View.GONE
                binding.txtError.visibility = View.GONE
            }
            NetworkState.LOADED -> {
                binding.loadingBar.visibility = View.GONE
                if(categoryViewModel.subCategoryList.value != null && categoryViewModel.subCategoryList.value!!.isNotEmpty()) {
                    binding.subTabLayout.visibility = View.VISIBLE
                }
                binding.txtError.visibility = View.GONE
                var item = productListViewModel.productList.value
                if( item == null || item !!.products.isEmpty()){
                    binding.productListRecyclerView.visibility = View.GONE
                    binding.noDataTv.visibility = View.VISIBLE
                }
                else {
                    binding.productListRecyclerView.visibility = View.VISIBLE
                    binding.noDataTv.visibility = View.GONE
                }
            }
            NetworkState.ERROR -> {
                binding.loadingBar.visibility = View.GONE
                binding.subTabLayout.visibility = View.GONE
                binding.productListRecyclerView.visibility = View.GONE
                binding.noDataTv.visibility = View.GONE
                binding.txtError.visibility = View.VISIBLE
            }
        }
    }
}