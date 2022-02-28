package com.zzuh.filot_shoppings.data.vo

data class ProductList (
    var products: List<Product>
    )

data class Product(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val size: String?
)
