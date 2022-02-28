package com.zzuh.filot_shoppings.data.repository

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zzuh.filot_shoppings.data.datasource.CategoryNetworkDataSource
import com.zzuh.filot_shoppings.data.datasource.ProductListNetworkDataSource
import com.zzuh.filot_shoppings.data.vo.Category
import com.zzuh.filot_shoppings.data.vo.ProductList

class SubCategoryRepository(lifecycleOwner: LifecycleOwner) {
    var dataSource: CategoryNetworkDataSource = CategoryNetworkDataSource()
    var subCategoryList = MutableLiveData<List<Category>>()
    var networkState = MutableLiveData<NetworkState>()

    init {
        dataSource.downloadCategoryListResponse.observe(lifecycleOwner, Observer {
            subCategoryList.postValue(it)
        })
        dataSource.networkState.observe(lifecycleOwner, Observer {
            networkState.postValue(it)
        })
    }

    fun fetchSubCategoryList(name: String): Unit{ dataSource.fetchCategoryList(name) }
    fun getProductListNetworkState(): LiveData<NetworkState> = dataSource.networkState
}