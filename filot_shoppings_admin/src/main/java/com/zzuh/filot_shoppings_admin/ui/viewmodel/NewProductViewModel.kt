package com.zzuh.filot_shoppings_admin.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.data.repository.NewProductRepository
import com.zzuh.filot_shoppings_admin.data.vo.MainCategory

class NewProductViewModel(private val token: String):ViewModel() {
    private val newProductRepository = NewProductRepository()
    val categoryNetworkState: LiveData<NetworkState> get() = newProductRepository.categoryNetworkState
    val categoryList: LiveData<List<MainCategory>> by lazy { newProductRepository.fetchCategoryAllList() }

    var thumbnailConfirm = false

    var name: String? = null
    var size: String? = null
    var description: String? = null
    var color: String? = null
    var categoryName: String? = null
    var path: String? = null

    var price: Int? = null
    var amount: Int? = null

    fun addProduct(context: Context){
        /*
         * 1. null check
         * 2. email 중복, 인증확인 check
         * 3. 비밀번호 일치 check
         */
        var msg: String? = null
        if(nullCheck())
            msg = "공란이 있습니다!"
        else if(!thumbnailConfirm)
            msg = "대표 이미지를 확정해 주세요!"
        else
            msg = size
            /*joinRepository.doJoin(JoinInfo(
                email = this.categoryName!!,
                password = this.path!!,
                detailAddress = this.size!!,
                roadAddress = this.description!!,
                name = this.name!!,
                phoneNumber = this.color!!
            ))*/

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