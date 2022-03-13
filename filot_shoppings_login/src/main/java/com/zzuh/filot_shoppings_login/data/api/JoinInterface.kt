package com.zzuh.filot_shoppings_login.data.api

import com.zzuh.filot_shoppings_login.data.vo.JoinInfo
import retrofit2.Call
import retrofit2.http.*

interface JoinInterface {
    @POST("/join")
    fun doJoin(
        @Body joinInfo: JoinInfo
    ): Call<Unit>
    @GET("/join/dup")
    fun emailDoubleCheck(
        @Query("email") email: String
    ): Call<Unit>
    @POST("/join")
    fun emailVerify(
        @Body joinInfo: JoinInfo
    ): Call<Unit>
    @GET("/join")
    fun verifyCheck(
        @Header("cookie") cookie: String,
        @Query("code") code: String
    ): Call<Unit>
}