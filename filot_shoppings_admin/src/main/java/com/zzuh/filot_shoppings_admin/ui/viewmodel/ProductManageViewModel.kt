package com.zzuh.filot_shoppings_admin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.data.repository.ProductManageRepository
import com.zzuh.filot_shoppings_admin.data.repository.UserManageRepository
import com.zzuh.filot_shoppings_admin.data.vo.Product
import com.zzuh.filot_shoppings_admin.data.vo.User

class ProductManageViewModel:ViewModel() {
    val repository = ProductManageRepository()
    val networkState: LiveData<NetworkState> get() = repository.productManageNetworkState
    val productList: LiveData<List<Product>> by lazy { repository.fetchProductAllList() }
}