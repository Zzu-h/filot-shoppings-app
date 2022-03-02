package com.zzuh.filot_shoppings_login.data.api

import com.zzuh.filot_shoppings_login.data.vo.JoinInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface JoinInterface {
    @POST("/join")
    fun doJoin(
        @Body joinInfo: JoinInfo
    ): Call<Unit>
    @POST("/mail-test-join")
    fun emailVerify(
        @Body joinInfo: JoinInfo
    ): Call<Unit>
    @POST("/verify-code")
    fun verifyCheck(
        @Body code: String
    ): Call<Unit>
}