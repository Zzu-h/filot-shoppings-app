package com.zzuh.filot_shoppings_admin.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings_admin.data.datasource.CategoryNetworkDataSource
import com.zzuh.filot_shoppings_admin.data.vo.MainCategory

class NewProductRepository {
    val categoryDataSource = CategoryNetworkDataSource()
    val categoryNetworkState: LiveData<NetworkState> get() = categoryDataSource.networkState

    fun fetchCategoryAllList(): LiveData<List<MainCategory>>{
        categoryDataSource.fetchCategoryAllList()
        return categoryDataSource.downloadCategoryListResponse
    }
}