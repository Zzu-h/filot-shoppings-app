package com.zzuh.filot_shoppings_admin.data.api

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ImageUploadInterface {
    @Multipart
    @POST("/admin/banners")
    fun uploadBannerImage(
        @Header("X-AUTH-TOKEN") token: String,
        @Part bannerFile : MultipartBody.Part
        ): Call<Unit>
    @Multipart
    @POST("/admin/products/image")
    fun uploadImageList(
        @Header("X-AUTH-TOKEN") token: String,
        @PartMap params: Map<String,@JvmSuppressWildcards RequestBody>,
        @Part files : ArrayList<MultipartBody.Part>
    ): Call<Unit>

    @Multipart
    @POST("/admin/products/{id}/image")
    fun insertImage(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("id") productId: Int,
        @Part image : MultipartBody.Part
    ): Call<Unit>

    @DELETE("/admin/products/{id}/image")
    fun deleteImage(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("id") productId: Int,
        @Body fileName: String
    ): Call<Unit>
}