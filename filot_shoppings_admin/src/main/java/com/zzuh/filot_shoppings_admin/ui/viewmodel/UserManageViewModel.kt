package com.zzuh.filot_shoppings_admin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.data.repository.UserManageRepository
import com.zzuh.filot_shoppings_admin.data.vo.User

class UserManageViewModel(private val token: String):ViewModel() {
    val repository = UserManageRepository()
    val networkState: LiveData<NetworkState> get() = repository.networkState
    val userList: LiveData<List<User>> by lazy {
        Log.d("Tester","loading")
        repository.fetchUserList(token)
    }
}

class UserManageViewModelFactory (private val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserManageViewModel::class.java)) {
            UserManageViewModel(token) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}