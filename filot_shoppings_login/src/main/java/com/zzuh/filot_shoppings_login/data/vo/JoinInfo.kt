package com.zzuh.filot_shoppings_login.data.vo

data class JoinInfo(
    val email: String,
    val name: String,
    val password: String,
    val detailAddress: String,
    val roadAddress: String,
    val phoneNumber: String,
    val authorities: String? = "ROLE_ADMIN",
)