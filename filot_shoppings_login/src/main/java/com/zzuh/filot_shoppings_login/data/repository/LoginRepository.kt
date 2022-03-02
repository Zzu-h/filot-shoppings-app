package com.zzuh.filot_shoppings_login.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings_login.data.datasource.LoginNetworkDataSource

class LoginRepository {
    val loginDataSource = LoginNetworkDataSource()
    val token: LiveData<String> get() = loginDataSource.token
    val networkState: LiveData<NetworkState> get() = loginDataSource.networkState

    fun doLogin(email: String, password: String){
        loginDataSource.doLogin(email, password)
    }
}