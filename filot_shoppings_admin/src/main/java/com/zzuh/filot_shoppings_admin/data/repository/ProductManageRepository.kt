package com.zzuh.filot_shoppings_admin.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings_admin.data.datasource.CategoryNetworkDataSource
import com.zzuh.filot_shoppings_admin.data.datasource.ImageUploadNetworkDataSource
import com.zzuh.filot_shoppings_admin.data.datasource.ProductManageNetworkDataSource
import com.zzuh.filot_shoppings_admin.data.vo.MainCategory
import com.zzuh.filot_shoppings_admin.data.vo.ProductDetails
import com.zzuh.filot_shoppings_admin.data.vo.ProductInfo

class ProductManageRepository {
    val categoryDataSource = CategoryNetworkDataSource()
    val productManageNetworkDataSource = ProductManageNetworkDataSource()
    val imageUploadDataSource = ImageUploadNetworkDataSource()

    val categoryNetworkState: LiveData<NetworkState> get() = categoryDataSource.networkState
    val productManageNetworkState: LiveData<NetworkState> get() = productManageNetworkDataSource.networkState
    val imageUploadNetworkState: LiveData<NetworkState> get() = imageUploadDataSource.networkState

    val newProductResponse: LiveData<ProductDetails> get() = productManageNetworkDataSource.newProductResponse

    fun fetchCategoryAllList(): LiveData<List<MainCategory>>{
        categoryDataSource.fetchCategoryAllList()
        return categoryDataSource.downloadCategoryListResponse
    }
    fun addNewProduct(token: String, path: String, productInfo: ProductInfo)
        = productManageNetworkDataSource.addNewProduct(token,path,productInfo)

    fun uploadImageList(token: String, productId: Int, category: String, pathList: List<String>)
        = imageUploadDataSource.uploadImageList(token, productId, category, pathList)
}