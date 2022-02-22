package com.zzuh.filot_shoppings.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.zzuh.filot_shoppings.data.vo.Product
import com.zzuh.filot_shoppings.databinding.MainFragmentBinding
import com.zzuh.filot_shoppings.ui.main.viewmodel.CategoryViewModel
import com.zzuh.filot_shoppings.ui.main.viewmodel.MainViewModel

class MainFragment(private var viewModel: CategoryViewModel) : Fragment() {
        lateinit var binding: MainFragmentBinding
    lateinit var adapter: ItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainFragmentBinding.inflate(layoutInflater)
        adapter = ItemListAdapter(emptyList(), requireContext())

        var gridLayout = GridLayoutManager(context, 2)
        binding.productListRecyclerView.layoutManager = gridLayout
        binding.productListRecyclerView.adapter = adapter
        viewModel.setCategoryName("main")
        viewModel.productList.observe(this, Observer{
            if(it == null || it.products.isEmpty()){
                binding.noDataTv.visibility = View.VISIBLE
                return@Observer
            }
            Log.d("tester","ok")
            adapter.updateData(it.products)
            adapter.notifyDataSetChanged()
        })
        viewModel.getProductList("main")
    }
}