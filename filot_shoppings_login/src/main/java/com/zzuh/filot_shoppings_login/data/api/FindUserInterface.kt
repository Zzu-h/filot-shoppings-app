package com.zzuh.filot_shoppings_login.data.api

import com.zzuh.filot_shoppings_login.data.vo.ChangeUserPassword
import com.zzuh.filot_shoppings_login.data.vo.JoinInfo
import com.zzuh.filot_shoppings_login.data.vo.SendEmail
import retrofit2.Call
import retrofit2.http.*

interface FindUserInterface {
    @GET("/join/dup")
    fun emailCheck(
        @Query("email") email: String
    ): Call<Unit>
    @POST("/users/password/email")
    fun sendCode(
        @Body sendEmail: SendEmail
    ): Call<Unit>
    @POST("/users/password/email/code")
    fun changePassword(
        @Body changeUserPassword: ChangeUserPassword
    ): Call<Unit>
}