package com.zzuh.filot_shoppings_admin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings_admin.data.repository.CategoryManageRepository
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.data.vo.MainCategory

class CategoryManageViewModel(private val token: String):ViewModel() {
    val repository = CategoryManageRepository()
    val networkState: LiveData<NetworkState> get() = repository.networkState
    val categoryList: LiveData<List<MainCategory>> by lazy { repository.fetchCategoryAllList() }

    fun addSubCategory(parent: String, child: String): Unit = repository.addSubCategory(token, parent, child)
}

class CategoryManageViewModelFactory (private val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CategoryManageViewModel::class.java)) {
            CategoryManageViewModel(token) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}