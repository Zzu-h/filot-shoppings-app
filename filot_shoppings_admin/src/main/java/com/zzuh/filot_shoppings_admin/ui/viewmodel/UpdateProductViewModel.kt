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

class UpdateProductViewModel(private val token: String,private val productId: Int):ViewModel() {
    private val productManageRepository = ProductManageRepository()
    val categoryNetworkState: LiveData<NetworkState> get() = productManageRepository.categoryNetworkState
    val productManageNetworkState: LiveData<NetworkState> get() = productManageRepository.productManageNetworkState
    val downloadProductResponse: LiveData<ProductDetails> get() = productManageRepository.downloadProductResponse

    val productDetails: LiveData<ProductDetails> by lazy { productManageRepository.fetchProductDetails(productId) }

    val categoryList: LiveData<List<MainCategory>> by lazy { productManageRepository.fetchCategoryAllList() }

    var thumbnailConfirm = false

    var name: String? = null
    var size: String? = null
    var description: String? = null
    var color: String? = null
    var categoryName: String? = null
    var path: String? = null

    var price: Int? = null
    var amount: Int? = null

    fun updateProduct(context: Context){
        var msg: String? = null
        if(nullCheck())
            msg = "공란이 있습니다!"
        else
            productManageRepository.updateProduct(token, productId, path!!, ProductInfo(
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

    fun updateImage(imagePathList: List<String>) {
        /*
        * 1. a, b를 비교, 다르면 update
        * 2. pathList is Empty 이고, livedata is not empty라면, delete
        * 3. pathList is not Empty이고, livedata is empty라면, insert
        * */
        val list = productDetails.value?.images
        if(list == null) return

        var lastIdx = 0
        for(i in 0..list.lastIndex){
            lastIdx = i
            if(imagePathList[i] != list[i]) productManageRepository.updateImage(token, productId = productId, i, imagePathList[i])
            else if(imagePathList[i].isEmpty()) productManageRepository.deleteImage(token, productId = productId, i)
        }
        for(i in lastIdx..4) if(imagePathList[i].isNotEmpty())
            productManageRepository.insertImage(token, productId = productId, i, imagePathList[i])
    }
}

class UpdateProductViewModelFactory (private val token: String, private val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UpdateProductViewModel::class.java)) {
            UpdateProductViewModel(token, id) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}