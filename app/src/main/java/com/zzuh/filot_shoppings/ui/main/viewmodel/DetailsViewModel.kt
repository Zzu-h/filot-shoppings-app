package com.zzuh.filot_shoppings.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.repository.ProductDetailsRepository
import com.zzuh.filot_shoppings.data.repository.ReviewRepository
import com.zzuh.filot_shoppings.data.vo.*

class DetailsViewModel(private val productId: Int) : ViewModel() {
    private val productDetailsRepository = ProductDetailsRepository()
    private val reviewRepository = ReviewRepository()
    val networkState: LiveData<NetworkState> get() = productDetailsRepository.networkState
    val reviewNetworkState: LiveData<NetworkState> get() = reviewRepository.networkState
    val product: LiveData<ProductDetails> by lazy{ productDetailsRepository.fetchProductList(productId) }
    val reviewList: LiveData<List<ReviewData>> by lazy { reviewRepository.fetchReviewList(productId) }

    val totPrice = MutableLiveData(0)
    var selectedList = MutableLiveData<SelectedProductList>()
    fun putProductBasket(token: String, selectedProductItem: SelectedProductItem){
        productDetailsRepository.putProductBasket(token, selectedProductItem)
    }
    fun removeReview(token: String, productId: Int, reviewId: Int)
        = reviewRepository.removeReview(token, productId, reviewId)
}