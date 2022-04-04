package com.zzuh.filot_shoppings.data.repository

import androidx.lifecycle.LiveData
import com.zzuh.filot_shoppings.data.datasource.ReviewNetworkDataSource
import com.zzuh.filot_shoppings.data.vo.ReviewData

class ReviewRepository() {
    private val dataSource = ReviewNetworkDataSource()
    val productList = dataSource.downloadReviewListResponse
    val networkState = dataSource.networkState

    fun fetchReviewList(productId: Int): LiveData<List<ReviewData>>{
        dataSource.fetchReviewList(productId)
        return dataSource.downloadReviewListResponse
    }
    fun removeReview(token: String, productId: Int, reviewId: Int)
        = dataSource.removeReview(token, productId, reviewId)

    fun addReview(token: String,email: String, productId: Int, imagePath: String, title: String, content: String, rate: Float)
        = dataSource.addReview(token = token,email = email, productId = productId, imagePath = imagePath, title = title, content = content, rate = rate)

    fun updateReview(token: String,email: String, productId: Int, reviewId: Int, imagePath: String, title: String, content: String, rate: Float)
            = dataSource.updateReview(token = token,email = email, productId = productId, reviewId = reviewId, imagePath = imagePath, title = title, content = content, rate = rate)
}