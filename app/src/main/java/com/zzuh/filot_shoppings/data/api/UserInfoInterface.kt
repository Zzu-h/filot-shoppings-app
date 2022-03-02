package com.zzuh.filot_shoppings.data.api

import com.zzuh.filot_shoppings.data.vo.Product
import com.zzuh.filot_shoppings.data.vo.UserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserInfoInterface {
    @GET("/users/{email}")
    fun getUserInfo(
        @Path("email") email: String,
        @Header("X-AUTH-TOKEN") token: String
    ): Call<UserInfo>
}