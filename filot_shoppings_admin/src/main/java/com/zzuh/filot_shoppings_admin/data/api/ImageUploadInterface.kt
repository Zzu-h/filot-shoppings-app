package com.zzuh.filot_shoppings_admin.data.api

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
    @POST("/image")
    fun uploadImageList(
        @Header("X-AUTH-TOKEN") token: String,
        @PartMap params: Map<String,@JvmSuppressWildcards RequestBody>,
        @Part imageList : List<MultipartBody.Part>
    ): Call<Unit>
}