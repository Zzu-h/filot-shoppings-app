package com.zzuh.filot_shoppings.data.api

import com.zzuh.filot_shoppings.data.vo.BasketItem
import retrofit2.Call
import retrofit2.http.*

interface BasketInterface {
    @GET("/users/baskets")
    fun getCartList(
        @Header("X-AUTH-TOKEN") token: String,
    ): Call<List<BasketItem>>
    /*@GET("/users/baskets/{baskets-id}")
    fun getCartList(
        @Header("cookie") token: String,
        @Path("baskets-id") basketId: String
    ): Call<Basket>*/
    @PUT("/users/baskets/{baskets-id}")
    fun updateProductCnt(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("baskets-id") productId: Int,
        @Query("cnt") cnt: Int
    ): Call<Unit>
}