package com.zzuh.filot_shoppings.ui.main.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductListViewModelFactory (private val lifecycleOwner: LifecycleOwner) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            ProductListViewModel(lifecycleOwner) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
class CategoryViewModelFactory (private val lifecycleOwner: LifecycleOwner) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            CategoryViewModel(lifecycleOwner) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}