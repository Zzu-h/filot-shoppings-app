package com.zzuh.filot_shoppings_admin.data.repository

import com.zzuh.filot_shoppings_admin.data.datasource.UserManageNetworkDataSource

class UserManageRepository {
    val dataSource = UserManageNetworkDataSource()
    fun fetchUserList(token: String){
        dataSource.fetchUserList(token)
    }
}