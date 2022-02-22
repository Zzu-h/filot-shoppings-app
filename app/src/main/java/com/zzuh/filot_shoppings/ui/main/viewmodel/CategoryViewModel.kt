package com.zzuh.filot_shoppings.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzuh.filot_shoppings.data.repository.ProductListRepository
import com.zzuh.filot_shoppings.data.vo.ProductList

class CategoryViewModel:ViewModel() {
    var categoryName = MutableLiveData<String>()
    var productListRepository = ProductListRepository()
    var productList = MutableLiveData<ProductList>()

    fun getProductList(name: String): LiveData<ProductList>{
        var list = productListRepository.fetchProductList(name)
        println("print: ${list.value?.products.toString()}")
        productList.postValue(list.value)

        return list
    }

    fun setCategoryName(name: String){this.categoryName.postValue(name)}
}