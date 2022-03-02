package com.zzuh.filot_shoppings.data.vo

data class UserInfo(
    val id: Int,
    val email: String,
    val name: String,
    val phoneNumber: String,
    val point: Int,
    val roles: List<String>,
    val detailAddress: String,
    val roadAddress: String
)