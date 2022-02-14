package com.zzuh.filot_shoppings.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.zzuh.filot_shoppings.databinding.FragmentCategoryBinding
import com.zzuh.filot_shoppings.ui.main.viewmodel.CategoryViewModel

class CategoryFragment(private val categoryViewModel: CategoryViewModel) : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        viewModel = categoryViewModel

        viewModel.tempData.observe(this, Observer {
            binding.categoryTitleTextView.text = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root
}