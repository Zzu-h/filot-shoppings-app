package com.zzuh.filot_shoppings.ui.main.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.zzuh.filot_shoppings.data.repository.ProductListRepository
import com.zzuh.filot_shoppings.data.vo.ProductList

class CategoryViewModel(activity: Activity):ViewModel() {
    var categoryName = MutableLiveData<String>()
    var productListRepository = ProductListRepository()
    var productList = MutableLiveData<ProductList>()
    init {
        productListRepository.productList.observe(context, Observer {

        })
    }
    fun getProductList(name: String): LiveData<ProductList>{
        var list = productListRepository.fetchProductList(name)
        productList.postValue(list.value)

        return list
    }

    fun setCategoryName(name: String){this.categoryName.postValue(name)}
}