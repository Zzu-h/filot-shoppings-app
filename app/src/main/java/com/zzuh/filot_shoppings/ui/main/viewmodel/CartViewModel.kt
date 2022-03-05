package com.zzuh.filot_shoppings.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzuh.filot_shoppings.data.repository.CartListRepository
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.vo.BasketItem

class CartViewModel(private val token: String):ViewModel() {
    val cartListRepository = CartListRepository()
    val networkState: LiveData<NetworkState> get() = cartListRepository.networkState
    val basket: LiveData<List<BasketItem>> by lazy { cartListRepository.fetchCartList(token) }
    val totPrice = MutableLiveData<Int>(0)

    fun updateProductCnt(basketId: Int, cnt: Int){cartListRepository.updateProductCnt(token, basketId, cnt)}
    fun deleteProductBasket(basketId: Int){cartListRepository.deleteProductBasket(token, basketId)}
}