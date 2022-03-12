package com.zzuh.filot_shoppings_admin.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings_admin.data.datasource.ImageUploadNetworkDataSource

class ImageUploadRepository {
    val dataSource = ImageUploadNetworkDataSource()
    val networkState: LiveData<NetworkState> get() = dataSource.networkState

    fun uploadBannerImage(token: String, path: String )
      = dataSource. uploadBannerImage(token, path)
}