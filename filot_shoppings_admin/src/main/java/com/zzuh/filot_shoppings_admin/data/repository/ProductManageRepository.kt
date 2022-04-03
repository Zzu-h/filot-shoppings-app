package com.zzuh.filot_shoppings_admin.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings_admin.data.datasource.CategoryNetworkDataSource
import com.zzuh.filot_shoppings_admin.data.datasource.ImageUploadNetworkDataSource
import com.zzuh.filot_shoppings_admin.data.datasource.ProductManageNetworkDataSource
import com.zzuh.filot_shoppings_admin.data.vo.MainCategory
import com.zzuh.filot_shoppings_admin.data.vo.Product
import com.zzuh.filot_shoppings_admin.data.vo.ProductDetails
import com.zzuh.filot_shoppings_admin.data.vo.ProductInfo

class ProductManageRepository {
    val categoryDataSource = CategoryNetworkDataSource()
    val productManageNetworkDataSource = ProductManageNetworkDataSource()
    val imageUploadDataSource = ImageUploadNetworkDataSource()

    val categoryNetworkState: LiveData<NetworkState> get() = categoryDataSource.networkState
    val productManageNetworkState: LiveData<NetworkState> get() = productManageNetworkDataSource.networkState
    val imageUploadNetworkState: LiveData<NetworkState> get() = imageUploadDataSource.networkState

    val downloadProductResponse: LiveData<ProductDetails> get() = productManageNetworkDataSource.downloadProductResponse

    fun fetchCategoryAllList(): LiveData<List<MainCategory>>{
        categoryDataSource.fetchCategoryAllList()
        return categoryDataSource.downloadCategoryListResponse
    }
    fun addNewProduct(token: String, path: String, productInfo: ProductInfo)
        = productManageNetworkDataSource.addNewProduct(token,path,productInfo)

    fun updateProduct(token: String, productId: Int, path: String, productInfo: ProductInfo)
            = productManageNetworkDataSource.updateProduct(token, productId, path, productInfo)

    fun uploadImageList(token: String, productId: Int, category: String, pathList: List<String>)
        = imageUploadDataSource.uploadImageList(token, productId, category, pathList)

    fun fetchProductAllList(): LiveData<List<Product>>{
        productManageNetworkDataSource.fetchProductAllList()
        return productManageNetworkDataSource.downloadProductListResponse
    }
    fun fetchProductDetails(id: Int): LiveData<ProductDetails>{
        productManageNetworkDataSource.fetchProductDetails(id)
        return productManageNetworkDataSource.downloadProductResponse
    }

    fun updateImage(token: String, productId: Int, i: Int, imagePath: String)
        = imageUploadDataSource.updateImage(token, productId, i, imagePath)
    fun deleteImage(token: String, productId: Int, i: Int)
        = imageUploadDataSource.deleteImage(token, productId, i)
    fun insertImage(token: String, productId: Int, i: Int, imagePath: String)
        = imageUploadDataSource.insertImage(token, productId, i, imagePath)
}