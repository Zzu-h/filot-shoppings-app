package com.zzuh.filot_shoppings.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.repository.ProductDetailsRepository
import com.zzuh.filot_shoppings.data.vo.Product
import com.zzuh.filot_shoppings.data.vo.ProductDetails
import com.zzuh.filot_shoppings.data.vo.SelectedProductItem
import com.zzuh.filot_shoppings.data.vo.SelectedProductList

class DetailsViewModel(private val productId: Int) : ViewModel() {
    val productDetailsRepository = ProductDetailsRepository()
    val networkState: LiveData<NetworkState> get() = productDetailsRepository.networkState
    val product: LiveData<ProductDetails> by lazy{
        productDetailsRepository.fetchProductList(productId)
    }
    val totPrice = MutableLiveData<Int>(0)
    var selectedList = MutableLiveData<SelectedProductList>()
    fun putProductBasket(token: String, selectedProductItem: SelectedProductItem){
        productDetailsRepository.putProductBasket(token, selectedProductItem)
    }
}