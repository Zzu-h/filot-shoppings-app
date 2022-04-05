package com.zzuh.filot_shoppings.data.api

import com.zzuh.filot_shoppings.data.vo.ReviewData
import com.zzuh.filot_shoppings.data.vo.UpdateReviewDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ReviewInterface {
    @GET("/products/{product_id}/reviews")
    fun getReviews(
        @Path("product_id") productId: Int,
        @Query("page") page: Int = 1
    ): Call<List<ReviewData>>

    @DELETE("/products/{product_id}/reviews")
    fun removeReview(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("product_id") productId: Int,
        @Query("review_id") reviewId: Int
    ): Call<Unit>

    @Multipart
    @POST("/products/{product_id}/reviews")
    fun addReview(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("product_id") productId: Int,
        @PartMap params: Map<String,@JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part,
    ): Call<Unit>

    @PUT("/products/{product_id}/reviews/{review_id}")
    fun updateReviewData(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("product_id") productId: Int,
        @Path("review_id") reviewId: Int,
        @Body updateReviewDTO: UpdateReviewDTO,
    ): Call<Unit>

    @Multipart
    @PUT("/products/{product_id}/reviews/{review_id}/image")
    fun updateReviewImage(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("product_id") productId: Int,
        @Path("review_id") reviewId: Int,
        @Part file: MultipartBody.Part,
    ): Call<Unit>
}