package com.zzuh.filot_shoppings.data.api

import com.zzuh.filot_shoppings.data.vo.Product
import com.zzuh.filot_shoppings.data.vo.ProductDetails
import com.zzuh.filot_shoppings.data.vo.ProductList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val API_KEY = "28067da966b4b33cf2e89be0850b658a"
const val BASE_URL = "https://filot-shopping.herokuapp.com/"

const val LAN_CODE = "ko-KR"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"

interface ProductInterface {
    @GET("/product-list/{name}")
    fun getProductList(
        @Path("name") name: String
    ): Call<List<Product>>

    @GET("/products/{id}")
    fun getProductDetails(
        @Path("id") id: Int
    ): Call<ProductDetails>
}