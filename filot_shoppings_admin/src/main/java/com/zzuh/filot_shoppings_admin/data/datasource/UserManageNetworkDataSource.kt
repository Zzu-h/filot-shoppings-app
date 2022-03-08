package com.zzuh.filot_shoppings_admin.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings_admin.data.api.BASE_URL
import com.zzuh.filot_shoppings_admin.data.api.UserManageInterface
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class UserManageNetworkDataSource {

    private val _networkState = MutableLiveData<NetworkState>()
    //private val _downloadCartListResponse = MutableLiveData<List<BasketItem>>()

    val networkState: LiveData<NetworkState> get() = _networkState
    //val downloadCartListResponse: LiveData<List<BasketItem>> get() = _downloadCartListResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(UserManageInterface::class.java)

    fun fetchUserList(token: String){
        val callGetList = api.getUserList(token)
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object :Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("fetchUserList-error", t.printStackTrace().toString())
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful)
                    Log.d("fetchUserList","${response.body()}")
                //_downloadCartListResponse.postValue(item)
                Log.d("fetchUserList","${response.raw()}")
                _networkState.postValue(NetworkState.LOADED)
            }
        })
    }
}