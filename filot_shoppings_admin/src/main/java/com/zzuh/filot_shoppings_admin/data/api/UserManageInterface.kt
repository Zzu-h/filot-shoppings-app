package com.zzuh.filot_shoppings_admin.data.api

import com.zzuh.filot_shoppings_admin.data.vo.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

const val BASE_URL = "https://filot-shopping.herokuapp.com/"

interface UserManageInterface {
    @GET("/admin/users/all")
    fun getUserList(
        @Header("X-AUTH-TOKEN") token: String
    ):Call<List<User>>
}