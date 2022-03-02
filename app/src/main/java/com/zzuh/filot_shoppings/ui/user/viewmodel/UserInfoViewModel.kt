package com.zzuh.filot_shoppings.ui.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings.data.repository.UserInfoRepository
import com.zzuh.filot_shoppings.data.vo.UserInfo

class UserInfoViewModel(private val email: String,private val token: String): ViewModel() {
    val userInfoRepository = UserInfoRepository()
    val userInfo: LiveData<UserInfo> by lazy{ userInfoRepository.fetchUserInfo(email, token) }
}

class UserInfoViewModelFactory (private val email: String, private val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            UserInfoViewModel(email, token) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}