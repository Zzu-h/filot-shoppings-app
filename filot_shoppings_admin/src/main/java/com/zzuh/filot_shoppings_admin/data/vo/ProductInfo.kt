package com.zzuh.filot_shoppings_admin.data.vo

data class ProductInfo(
    val name: String,
    val price: Int,
    val amount: Int,
    val size: String,
    val description: String,
    val color: String,
    val categoryName: String
)

data class ProductDetails (
    val id: String,
    val name: String,
    val price: Int,
    val size: String,
    val amount: Int,
    val imageUrl: String?,
    val description: String?,
    val deliveryPrice: Int?,
    val images: MutableList<String>?,
    val colors: List<String>?
)