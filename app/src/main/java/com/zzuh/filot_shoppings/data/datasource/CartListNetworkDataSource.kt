package com.zzuh.filot_shoppings.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings.data.api.BASE_URL
import com.zzuh.filot_shoppings.data.api.BasketInterface
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.vo.BasketItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class CartListNetworkDataSource {

    private val _networkState = MutableLiveData<NetworkState>()
    private val _downloadCartListResponse = MutableLiveData<List<BasketItem>>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val downloadCartListResponse: LiveData<List<BasketItem>> get() = _downloadCartListResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(BasketInterface::class.java)

    fun fetchCartList(token: String){
        val callGetList = api.getCartList(token)
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object :Callback<List<BasketItem>>{
            override fun onFailure(call: Call<List<BasketItem>>, t: Throwable) {
                Log.d("fetchCartList-error", t.printStackTrace().toString())
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<List<BasketItem>>, response: Response<List<BasketItem>>) {
                if(response.isSuccessful)
                    Log.d("fetchCartList","${response.body()}")
                var item = (response.body() as List<BasketItem>)
                _downloadCartListResponse.postValue(item)
                _networkState.postValue(NetworkState.LOADED)
            }
        })
    }
    fun updateProductCnt(token: String, id: Int, cnt: Int){
        val callGetList = api.updateProductCnt(token, id, cnt)
        //_networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object :Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                t.printStackTrace()
                //_networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful)
                    Log.d("updateProductCnt","${response}")
                //_networkState.postValue(NetworkState.LOADED)
            }
        })
    }
}