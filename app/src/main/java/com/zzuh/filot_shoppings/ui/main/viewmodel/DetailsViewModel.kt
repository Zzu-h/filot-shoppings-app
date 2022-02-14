package com.zzuh.filot_shoppings.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzuh.filot_shoppings.data.vo.Product
import com.zzuh.filot_shoppings.data.vo.SelectedProductList

class DetailsViewModel(private val productId: Int) : ViewModel() {
    var product = MutableLiveData<Product>()
    var selectedList = MutableLiveData<SelectedProductList>()

}