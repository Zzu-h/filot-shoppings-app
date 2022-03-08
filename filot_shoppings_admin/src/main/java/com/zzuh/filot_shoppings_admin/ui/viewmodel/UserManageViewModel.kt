package com.zzuh.filot_shoppings_admin.ui.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings_admin.data.repository.UserManageRepository

class UserManageViewModel(private val token: String):ViewModel() {
    val repository = UserManageRepository()

    fun fetchUserList(){
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