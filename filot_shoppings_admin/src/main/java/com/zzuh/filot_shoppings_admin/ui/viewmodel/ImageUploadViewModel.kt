package com.zzuh.filot_shoppings_admin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings_admin.data.repository.ImageUploadRepository
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.data.repository.UserManageRepository
import com.zzuh.filot_shoppings_admin.data.vo.User

class ImageUploadViewModel(private val token: String):ViewModel() {
    val repository = ImageUploadRepository()
    val networkState: LiveData<NetworkState> get() = repository.networkState

    fun uploadBannerImage(path: String ) = repository.uploadBannerImage(token, path)
}

class ImageUploadViewModelFactory (private val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ImageUploadViewModel::class.java)) {
            ImageUploadViewModel(token) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}