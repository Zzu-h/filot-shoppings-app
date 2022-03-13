package com.zzuh.filot_shoppings_login.data.vo

data class ChangeUserPassword(
    val email: String,
    val code: String,
    val newPassword: String,
)
data class SendEmail(val email: String)