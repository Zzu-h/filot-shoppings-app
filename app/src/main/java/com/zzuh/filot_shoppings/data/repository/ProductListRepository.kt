package com.zzuh.filot_shoppings.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings.data.datasource.ProductListNetworkDataSource
import com.zzuh.filot_shoppings.data.vo.ProductList

class ProductListRepository {
    var dataSource: ProductListNetworkDataSource = ProductListNetworkDataSource()
    var productList = MutableLiveData<ProductList>()

    fun fetchProductList(name: String): LiveData<ProductList>{
        dataSource.fetchProductList(name)
        return dataSource.downloadProductListResponse
    }

    fun getProductListNetworkState(): LiveData<NetworkState> = dataSource.networkState
}