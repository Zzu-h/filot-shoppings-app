package com.zzuh.filot_shoppings.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings.data.api.BASE_URL
import com.zzuh.filot_shoppings.data.api.ProductInterface
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.vo.ProductList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ProductListNetworkDataSource {

    private val _networkState = MutableLiveData<NetworkState>()
    private val _downloadProductListResponse = MutableLiveData<ProductList>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val downloadProductListResponse: LiveData<ProductList> get() = _downloadProductListResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(ProductInterface::class.java)

    fun fetchProductList(name: String){
        val callGetList = api.getProductList(name)
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object :Callback<ProductList>{
            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                Log.d("fetchProductList", t.printStackTrace().toString())
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                _downloadProductListResponse.postValue(response.body() as ProductList)
                _networkState.postValue(NetworkState.LOADED)
            }
        })
    }
}