package com.zzuh.filot_shoppings.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings.data.datasource.CartListNetworkDataSource
import com.zzuh.filot_shoppings.data.vo.BasketItem

class CartListRepository {
    val cartListDataSource = CartListNetworkDataSource()
    val networkState: LiveData<NetworkState> get() = cartListDataSource.networkState

    fun fetchCartList(token: String): LiveData<List<BasketItem>>{
        cartListDataSource.fetchCartList(token)
        return cartListDataSource.downloadCartListResponse
    }
    fun updateProductCnt(token: String, id: Int, cnt: Int):Unit
        = cartListDataSource.updateProductCnt(token, id, cnt)
    fun deleteProductBasket(token: String, basketId: Int){cartListDataSource.deleteProductBasket(token, basketId)}
}