package com.zzuh.filot_shoppings.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings.data.api.BASE_URL
import com.zzuh.filot_shoppings.data.api.ProductInterface
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.vo.ProductDetails
import com.zzuh.filot_shoppings.data.vo.SelectedProductItem
import com.zzuh.filot_shoppings.data.vo.SendProductToBasket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ProductDetailsNetworkDataSource {

    private val _networkState = MutableLiveData<NetworkState>()
    private val _downloadProductDetailsResponse = MutableLiveData<ProductDetails>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val downloadProductDetailsResponse: LiveData<ProductDetails> get() = _downloadProductDetailsResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(ProductInterface::class.java)

    fun fetchProductDetails(id: Int){
        val callGetDetails = api.getProductDetails(id)
        //_networkState.postValue(NetworkState.LOADING)
        callGetDetails.enqueue(object :Callback<ProductDetails>{
            override fun onFailure(call: Call<ProductDetails>, t: Throwable) {
                Log.d("fetchProductDetails", t.printStackTrace().toString())
                //_networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<ProductDetails>, response: Response<ProductDetails>) {
                _downloadProductDetailsResponse.postValue(response.body() as ProductDetails)
                //_networkState.postValue(NetworkState.LOADED)
            }
        })
    }
    fun putProductBasket(token: String, selectedProductItem: SelectedProductItem){
        Log.d("putProductBasket", "set")
        val callGetDetails = api.putProductBasket(token,
            selectedProductItem.id,
            SendProductToBasket(
                selectedProductItem.count,
                selectedProductItem.color,
                selectedProductItem.size.toString()
            )
        )
        _networkState.postValue(NetworkState.LOADING)
        Log.d("putProductBasket", "start")
        callGetDetails.enqueue(object :Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("putProductBasket", t.printStackTrace().toString())
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful)
                    _networkState.postValue(NetworkState.LOADED)
                else
                    _networkState.postValue(NetworkState.ERROR)
                Log.d("putProductBasket", "${response.raw()}")
            }
        })
        Log.d("putProductBasket", "end")
    }
}