package com.zzuh.filot_shoppings_admin.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.data.repository.ProductManageRepository
import com.zzuh.filot_shoppings_admin.data.vo.MainCategory
import com.zzuh.filot_shoppings_admin.data.vo.ProductDetails
import com.zzuh.filot_shoppings_admin.data.vo.ProductInfo

class NewProductViewModel(private val token: String):ViewModel() {
    private val productManageRepository = ProductManageRepository()
    val categoryNetworkState: LiveData<NetworkState> get() = productManageRepository.categoryNetworkState
    val productManageNetworkState: LiveData<NetworkState> get() = productManageRepository.productManageNetworkState
    val newProductResponse: LiveData<ProductDetails> get() = productManageRepository.newProductResponse

    val categoryList: LiveData<List<MainCategory>> by lazy { productManageRepository.fetchCategoryAllList() }

    var thumbnailConfirm = false
    var productId = -1

    var name: String? = null
    var size: String? = null
    var description: String? = null
    var color: String? = null
    var categoryName: String? = null
    var path: String? = null

    var price: Int? = null
    var amount: Int? = null

    fun addProduct(context: Context){
        var msg: String? = null
        if(nullCheck())
            msg = "공란이 있습니다!"
        else if(!thumbnailConfirm)
            msg = "대표 이미지를 확정해 주세요!"
        else
            productManageRepository.addNewProduct(token,path!!, ProductInfo(
                name = name!!,
                price = price!!,
                amount = amount!!,
                size = size!!,
                description = description!!,
                color = color!!,
                categoryName = categoryName!!
            ))

        if(msg != null)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
    private fun nullCheck(): Boolean{
        var isNull = false

        if(categoryName == null || categoryName == "") isNull = true
        if(path == null || path == "") isNull = true
        if(size == null|| size == "") isNull = true
        if(description == null|| description == "") isNull = true
        if(name == null|| name == "") isNull = true
        if(color == null|| color == "") isNull = true
        if(price == null|| amount == null) isNull = true

        return isNull
    }

    fun uploadImage(imagePathList: List<String>)
        = productManageRepository.uploadImageList(token, productId = productId, categoryName!!, imagePathList)
}

class NewProductViewModelFactory (private val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NewProductViewModel::class.java)) {
            NewProductViewModel(token) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}