package com.zzuh.filot_shoppings.ui.main.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.*
import com.zzuh.filot_shoppings.data.repository.ProductListRepository
import com.zzuh.filot_shoppings.data.vo.ProductList

class ProductListViewModel(lifecycleOwner: LifecycleOwner):ViewModel() {
    var categoryName = MutableLiveData<String>()
    var productListRepository = ProductListRepository(lifecycleOwner)
    var productList = MutableLiveData<ProductList>()
    init {
        productListRepository.productList.observe(lifecycleOwner, Observer {
            productList.postValue(it)
        })
    }
    fun getProductList(name: String): Unit{
        var list = productListRepository.fetchProductList(name)
    }

    fun setCategoryName(name: String){this.categoryName.postValue(name)}
}