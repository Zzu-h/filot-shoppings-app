package com.zzuh.filot_shoppings.ui.main.viewmodel

import androidx.lifecycle.*
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.repository.ProductListRepository
import com.zzuh.filot_shoppings.data.vo.ProductList

class ProductListViewModel(lifecycleOwner: LifecycleOwner):ViewModel() {
    var categoryName = MutableLiveData<String>()
    private var productListRepository = ProductListRepository(lifecycleOwner)
    var productList = MutableLiveData<ProductList>()
    var networkState = MutableLiveData<NetworkState>()
    init {
        productListRepository.productList.observe(lifecycleOwner, Observer {
            productList.postValue(it)
        })
        productListRepository.networkState.observe(lifecycleOwner, Observer {
            networkState.postValue(it)
        })
    }
    fun getProductList(name: String): Unit = productListRepository.fetchProductList(name)
    fun setCategoryName(name: String){this.categoryName.postValue(name)}
}