package com.zzuh.filot_shoppings_admin.data.api

import com.zzuh.filot_shoppings_admin.data.vo.MainCategory
import com.zzuh.filot_shoppings_admin.data.vo.NewCategory
import retrofit2.Call
import retrofit2.http.*

interface CategoryInterface {
    @GET("/categories/main")
    fun getCategoryAllList(): Call<List<MainCategory>>

    @POST("/admin/categories")
    fun addSubCategory(
        @Header("X-AUTH-TOKEN") token: String,
        @Body newCategory: NewCategory
        ): Call<MainCategory>
}