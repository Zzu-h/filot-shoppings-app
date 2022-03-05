package com.zzuh.filot_shoppings.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings.data.datasource.ProductDetailsNetworkDataSource
import com.zzuh.filot_shoppings.data.vo.ProductDetails
import com.zzuh.filot_shoppings.data.vo.SelectedProductItem

class ProductDetailsRepository {
    var dataSource: ProductDetailsNetworkDataSource = ProductDetailsNetworkDataSource()
    fun fetchProductList(id: Int): LiveData<ProductDetails>{
        dataSource.fetchProductDetails(id)
        return dataSource.downloadProductDetailsResponse
    }

    val networkState: LiveData<NetworkState> get() = dataSource.networkState
    fun putProductBasket(token: String, selectedProductItem: SelectedProductItem){
        dataSource.putProductBasket(token, selectedProductItem)
    }
}