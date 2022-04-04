package com.zzuh.filot_shoppings.data.vo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ReviewData(
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    val rate: Float,
    @SerializedName("createdAt")
    val date: String,
    val userName: String,
    val email: String,
    val imageUrl: String
): Serializable