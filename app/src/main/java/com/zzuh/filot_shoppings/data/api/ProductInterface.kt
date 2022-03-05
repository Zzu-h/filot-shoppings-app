package com.zzuh.filot_shoppings.data.api

import com.zzuh.filot_shoppings.data.vo.Product
import com.zzuh.filot_shoppings.data.vo.ProductDetails
import com.zzuh.filot_shoppings.data.vo.ProductList
import com.zzuh.filot_shoppings.data.vo.SendProductToBasket
import retrofit2.Call
import retrofit2.http.*

const val BASE_URL = "https://filot-shopping.herokuapp.com/"

const val LAN_CODE = "ko-KR"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"

interface ProductInterface {
    @GET("/products/{name}")
    fun getProductList(
        @Path("name") name: String
    ): Call<List<Product>>

    @GET("/product/{id}")
    fun getProductDetails(
        @Path("id") id: Int
    ): Call<ProductDetails>

    @POST("/products/{id}/baskets")
    fun putProductBasket(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("id") productId: Int,
        @Body sendProductToBasket: SendProductToBasket
    ): Call<Unit>
}