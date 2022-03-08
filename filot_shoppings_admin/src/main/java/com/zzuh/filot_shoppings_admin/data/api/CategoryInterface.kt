package com.zzuh.filot_shoppings_admin.data.api

import com.zzuh.filot_shoppings_admin.data.vo.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryInterface {
    @GET("/category-list/{name}")
    fun getCategoryList(
        @Path("name") name: String
    ): Call<List<Category>>
}