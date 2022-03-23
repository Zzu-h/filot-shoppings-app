package com.zzuh.filot_shoppings_admin.data.vo

data class MainCategory(
    val id: Int,
    val name: String,
    val children: List<Category>
)
data class Category(
    val id: Int,
    val name: String,
)
data class NewSubCategory(
    val parentName: String,
    val name: String
)
data class NewMainCategory(
    val name: String
)

