package com.zzuh.filot_shoppings.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings.data.datasource.UserInfoNetworkDataSource
import com.zzuh.filot_shoppings.data.vo.UserInfo

class UserInfoRepository {
    val userInfoNetworkDataSource = UserInfoNetworkDataSource()

    fun fetchUserInfo(email:String, token: String): LiveData<UserInfo>{
        userInfoNetworkDataSource.fetchUserInfo(email, token)
        return userInfoNetworkDataSource.downloadUserInfoResponse
    }
}