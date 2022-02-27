package com.zzuh.filot_shoppings.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.zzuh.filot_shoppings.databinding.FragmentCategoryBinding
import com.zzuh.filot_shoppings.ui.main.viewmodel.ProductListViewModel

class CategoryFragment(private val productListViewModel: ProductListViewModel) : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    lateinit var viewModel: ProductListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        viewModel = productListViewModel

        viewModel.categoryName.observe(this, Observer {
            binding.categoryTitleTextView.text = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root
}