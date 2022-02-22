package com.zzuh.filot_shoppings.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings.data.datasource.ProductListNetworkDataSource
import com.zzuh.filot_shoppings.data.vo.ProductList

class ProductListRepository {
    var dataSource: ProductListNetworkDataSource = ProductListNetworkDataSource()
    init {
    }
    fun fetchProductList(name: String): LiveData<ProductList>{
        dataSource.fetchProductList(name)
        return dataSource.downloadProductListResponse
    }

    fun getProductListNetworkState(): LiveData<NetworkState> = dataSource.networkState
}