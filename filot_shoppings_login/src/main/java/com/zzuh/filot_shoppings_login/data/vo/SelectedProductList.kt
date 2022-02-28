package com.zzuh.filot_shoppings.data.vo

data class SelectedProductList (
    val list: MutableList<Product>
    )

data class SelectedProductItem(
    val id: Int,
    val name: String,
    val color: String,
    val size: Int,
    val price: Int,
    val count: Int = 1
)