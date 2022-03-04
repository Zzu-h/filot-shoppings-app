package com.zzuh.filot_shoppings.data.vo

data class BasketItem(
    val id: Int,
    val productId: Int,
    val productName: String,
    val productPrice: Int,
    val totalPrice: Int,
    val productOption: String,
    var selectedCount: Int,
    val productUrl: String,
)