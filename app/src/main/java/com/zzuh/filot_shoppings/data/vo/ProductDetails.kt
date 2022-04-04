package com.zzuh.filot_shoppings.data.vo

import java.io.Serializable

data class ProductDetails (
    val id: String,
    val name: String,
    val price: Int,
    val size: String,
    val description: String?,
    val deliveryPrice: Int?,
    val amount: Int,
    val images: List<String>,
    val colors: List<String>
    ): Serializable