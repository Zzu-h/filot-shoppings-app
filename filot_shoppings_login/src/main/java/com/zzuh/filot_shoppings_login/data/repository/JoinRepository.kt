package com.zzuh.filot_shoppings_login.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings_login.data.datasource.JoinNetworkDataSource
import com.zzuh.filot_shoppings_login.data.vo.JoinInfo

class JoinRepository {
    val joinNetworkDataSource = JoinNetworkDataSource()
    val networkState: LiveData<NetworkState> get() = joinNetworkDataSource.networkState
    val emailDoubleCheck: LiveData<NetworkState> get() = joinNetworkDataSource.emailDoubleCheck

    fun doJoin(joinInfo: JoinInfo) {
        joinNetworkDataSource.doJoin(joinInfo)
    }
    fun checkCode(code: String){
        joinNetworkDataSource.checkCode(code)
    }
    fun doubleCheck(email: String){
        joinNetworkDataSource.doubleEmailCheck(email)
    }
}