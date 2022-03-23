package com.zzuh.filot_shoppings_admin.data.api

import com.zzuh.filot_shoppings_admin.data.vo.MainCategory
import com.zzuh.filot_shoppings_admin.data.vo.NewMainCategory
import com.zzuh.filot_shoppings_admin.data.vo.NewSubCategory
import retrofit2.Call
import retrofit2.http.*

interface CategoryInterface {
    @GET("/categories/main")
    fun getCategoryAllList(): Call<List<MainCategory>>

    @POST("/admin/categories")
    fun addSubCategory(
        @Header("X-AUTH-TOKEN") token: String,
        @Body newSubCategory: NewSubCategory
        ): Call<MainCategory>

    @POST("/admin/categories")
    fun addMainCategory(
        @Header("X-AUTH-TOKEN") token: String,
        @Body newSubCategory: NewMainCategory
    ): Call<MainCategory>
}