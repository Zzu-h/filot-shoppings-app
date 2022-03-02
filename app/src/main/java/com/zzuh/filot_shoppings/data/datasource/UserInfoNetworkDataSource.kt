package com.zzuh.filot_shoppings.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings.data.api.BASE_URL
import com.zzuh.filot_shoppings.data.api.ProductInterface
import com.zzuh.filot_shoppings.data.api.UserInfoInterface
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.vo.ProductDetails
import com.zzuh.filot_shoppings.data.vo.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class UserInfoNetworkDataSource {

    private val _networkState = MutableLiveData<NetworkState>()
    private val _downloadUserInfoResponse = MutableLiveData<UserInfo>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val downloadUserInfoResponse: LiveData<UserInfo> get() = _downloadUserInfoResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(UserInfoInterface::class.java)

    fun fetchUserInfo(email:String, token: String){
        val callGetDetails = api.getUserInfo(email, token)
        _networkState.postValue(NetworkState.LOADING)
        callGetDetails.enqueue(object :Callback<UserInfo>{
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d("fetchUserInfo", t.printStackTrace().toString())
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                _downloadUserInfoResponse.postValue(response.body() as UserInfo)
                _networkState.postValue(NetworkState.LOADED)
            }
        })
    }
}