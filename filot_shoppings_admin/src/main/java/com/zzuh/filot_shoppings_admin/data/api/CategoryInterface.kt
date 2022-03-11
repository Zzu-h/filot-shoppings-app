package com.zzuh.filot_shoppings_admin.data.api

import com.zzuh.filot_shoppings_admin.data.vo.MainCategory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryInterface {
    @GET("/categories/main")
    fun getCategoryAllList(): Call<List<MainCategory>>

}