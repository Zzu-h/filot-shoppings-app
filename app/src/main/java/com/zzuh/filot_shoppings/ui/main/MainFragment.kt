package com.zzuh.filot_shoppings.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.zzuh.filot_shoppings.databinding.MainFragmentBinding
import com.zzuh.filot_shoppings.ui.main.viewmodel.MainViewModel

class MainFragment(private var viewModel: MainViewModel) : Fragment() {
        lateinit var binding: MainFragmentBinding
    lateinit var adapter: ItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        adapter = ItemListAdapter(emptyList(), requireContext())

        var gridLayout = GridLayoutManager(context, 2)
        binding.productListRecyclerView.layoutManager = gridLayout
        binding.productListRecyclerView.adapter = adapter
    }
}