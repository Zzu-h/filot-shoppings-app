package com.zzuh.filot_shoppings_admin.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings_admin.data.datasource.CategoryNetworkDataSource
import com.zzuh.filot_shoppings_admin.data.datasource.UserManageNetworkDataSource
import com.zzuh.filot_shoppings_admin.data.vo.MainCategory
import com.zzuh.filot_shoppings_admin.data.vo.User

class CategoryManageRepository {
    val dataSource = CategoryNetworkDataSource()
    val networkState: LiveData<NetworkState> get() = dataSource.networkState

    fun fetchCategoryAllList(): LiveData<List<MainCategory>>{
        dataSource.fetchCategoryAllList()
        return dataSource.downloadCategoryListResponse
    }
    fun addCategory(token: String, parent: String, child: String) {
        if(parent == "main") dataSource.addMainCategory(token, child)
        else dataSource.addSubCategory(token, parent, child)
    }
}