package com.zzuh.filot_shoppings_admin.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings_admin.data.datasource.UserManageNetworkDataSource
import com.zzuh.filot_shoppings_admin.data.vo.User

class UserManageRepository {
    val dataSource = UserManageNetworkDataSource()
    val networkState: LiveData<NetworkState> get() = dataSource.networkState

    fun fetchUserList(token: String): LiveData<List<User>>{
        dataSource.fetchUserList(token)
        return dataSource.downloadUserListResponse
    }
}