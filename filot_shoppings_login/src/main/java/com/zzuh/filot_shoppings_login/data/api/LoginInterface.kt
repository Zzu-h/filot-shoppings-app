package com.zzuh.filot_shoppings_login.data.api

import com.zzuh.filot_shoppings_login.data.vo.LoginInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

const val BASE_URL = "https://filot-shopping.herokuapp.com/"
interface LoginInterface {
    @POST("login")
    fun doLogin(@Body loginInfo: LoginInfo): Call<String>
}