package com.zzuh.filot_shoppings_login.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings_login.data.api.BASE_URL
import com.zzuh.filot_shoppings_login.data.api.JoinInterface
import com.zzuh.filot_shoppings_login.data.repository.NetworkState
import com.zzuh.filot_shoppings_login.data.vo.JoinInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class JoinNetworkDataSource {
    private val _networkState = MutableLiveData<NetworkState>()

    val networkState: LiveData<NetworkState> get() = _networkState

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(JoinInterface::class.java)

    fun doJoin(joinInfo: JoinInfo){
        val callGetList = api.emailVerify(joinInfo)
        Log.d("email", joinInfo.email)
        _networkState.postValue(NetworkState.CHECKINGCODE)
        callGetList.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("emailVerify", "error")
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("emailVerify",response.toString())
            }
        })
    }
    fun checkCode(code: String){
        val callGetList = api.verifyCheck(code)
        _networkState.postValue(NetworkState.LOADING)
        callGetList.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("verifyCheck", "error")
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("verifyCheck",response.toString())
                _networkState.postValue(
                    if(response.isSuccessful)
                        NetworkState.LOADED
                    else
                        NetworkState.ERROR
                )
            }
        })
    }
    fun doubleEmailCheck(email: String){

    }
}