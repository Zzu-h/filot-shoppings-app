package com.zzuh.filot_shoppings.data.vo

data class SelectedProductList (
    val list: MutableList<SelectedProductItem>
    )

data class SelectedProductItem(
    val id: Int,
    val name: String,
    val color: String,
    val size: Int,
    val price: Int,
    var count: Int = 1
)
data class SendProductToBasket(
    var count: Int,
    val color: String,
    val size: String,
)