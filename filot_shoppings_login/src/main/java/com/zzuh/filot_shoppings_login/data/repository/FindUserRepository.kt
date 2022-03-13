package com.zzuh.filot_shoppings_login.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings_login.data.datasource.FindUserNetworkDataSource
import com.zzuh.filot_shoppings_login.data.datasource.JoinNetworkDataSource
import com.zzuh.filot_shoppings_login.data.vo.JoinInfo

class FindUserRepository {
    val findUserNetworkDataSource = FindUserNetworkDataSource()
    val networkState: LiveData<NetworkState> get() = findUserNetworkDataSource.networkState
    val emailCheck: LiveData<NetworkState> get() = findUserNetworkDataSource.emailCheck

    fun changePassword(email: String, password: String ,code: String){
        findUserNetworkDataSource.changePassword(
            email = email,
            password = password,
            code = code)
    }
    fun sendCode(email: String){
        findUserNetworkDataSource.sendCode(email)
    }
    fun emailCheck(email: String){
        findUserNetworkDataSource.emailCheck(email)
    }
}