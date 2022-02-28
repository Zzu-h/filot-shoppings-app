package com.zzuh.filot_shoppings.data.repository

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zzuh.filot_shoppings.data.datasource.ProductListNetworkDataSource
import com.zzuh.filot_shoppings.data.vo.ProductList

class ProductListRepository(lifecycleOwner: LifecycleOwner) {
    var dataSource: ProductListNetworkDataSource = ProductListNetworkDataSource()
    var productList = MutableLiveData<ProductList>()
    var networkState = MutableLiveData<NetworkState>()

    init {
        dataSource.downloadProductListResponse.observe(lifecycleOwner, Observer {
            productList.postValue(it)
        })
        dataSource.networkState.observe(lifecycleOwner, Observer {
            networkState.postValue(it)
        })
    }

    fun fetchProductList(name: String): Unit{ dataSource.fetchProductList(name) }
    fun getProductListNetworkState(): LiveData<NetworkState> = dataSource.networkState
}