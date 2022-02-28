package com.zzuh.filot_shoppings.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.zzuh.filot_shoppings.databinding.MainFragmentBinding
import com.zzuh.filot_shoppings.ui.main.viewmodel.ProductListViewModel

class MainFragment(private var viewModel: ProductListViewModel) : Fragment() {
    lateinit var binding: MainFragmentBinding
    lateinit var adapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainFragmentBinding.inflate(layoutInflater)
        adapter = ProductListAdapter(emptyList(), requireContext())

        var gridLayout = GridLayoutManager(context, 2)
        binding.productListRecyclerView.layoutManager = gridLayout
        binding.productListRecyclerView.adapter = adapter
        viewModel.setCategoryName("main")
        viewModel.productList.observe(this, Observer{
            if(it == null || it.products.isEmpty()){
                binding.noDataTv.visibility = View.VISIBLE
                binding.productListRecyclerView.visibility = View.GONE
                return@Observer
            }
            Log.d("tester","ok")
            adapter.updateData(it.products)
            adapter.notifyDataSetChanged()
        })
        viewModel.getProductList("main")
    }
}