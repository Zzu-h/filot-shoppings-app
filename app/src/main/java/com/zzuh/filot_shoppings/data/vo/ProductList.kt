package com.zzuh.filot_shoppings.data.vo

data class ProductList (
    val list: List<Product>
    )

data class Product(
    val id: Int,
    val name: String,
    val imagePath: String,
    val price: Int,
    val deliveryPrice: Int?
)