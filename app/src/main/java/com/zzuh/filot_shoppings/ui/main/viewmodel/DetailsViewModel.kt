package com.zzuh.filot_shoppings.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzuh.filot_shoppings.data.repository.ProductDetailsRepository
import com.zzuh.filot_shoppings.data.vo.Product
import com.zzuh.filot_shoppings.data.vo.ProductDetails
import com.zzuh.filot_shoppings.data.vo.SelectedProductList

class DetailsViewModel(private val productId: Int) : ViewModel() {
    val productDetailsRepository = ProductDetailsRepository()
    val product: LiveData<ProductDetails> by lazy{
        productDetailsRepository.fetchProductList(productId)
    }
    var selectedList = MutableLiveData<SelectedProductList>()
}