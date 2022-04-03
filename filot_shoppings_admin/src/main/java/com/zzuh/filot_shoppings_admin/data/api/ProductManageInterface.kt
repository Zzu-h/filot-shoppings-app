package com.zzuh.filot_shoppings_admin.data.api

import com.zzuh.filot_shoppings_admin.data.vo.Product
import com.zzuh.filot_shoppings_admin.data.vo.ProductDetails
import com.zzuh.filot_shoppings_admin.data.vo.ProductInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ProductManageInterface {
    @Multipart
    @POST("/admin/products/")
    fun addNewProduct(
        @Header("X-AUTH-TOKEN") token: String,
        @PartMap params: Map<String,@JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part,
    ): Call<ProductDetails>

    @GET("/products/main/")
    fun getAllProducts(): Call<List<Product>>

    @GET("/product/{id}")
    fun getProductDetails(
        @Path("id") id: Int
    ): Call<ProductDetails>
}