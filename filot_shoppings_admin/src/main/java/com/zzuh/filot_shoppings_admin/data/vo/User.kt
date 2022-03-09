package com.zzuh.filot_shoppings_admin.data.vo

data class User(
    val id: Int,
    val email: String,
    val name: String,
    val phoneNumber: String,
    val point: Int,
    val roles: List<String>,
    val detailAddress: String,
    val roadAddress: String
)