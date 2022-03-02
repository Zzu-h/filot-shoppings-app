package com.zzuh.filot_shoppings_login.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings_login.data.api.BASE_URL
import com.zzuh.filot_shoppings_login.data.api.LoginInterface
import com.zzuh.filot_shoppings_login.data.repository.NetworkState
import com.zzuh.filot_shoppings_login.data.vo.LoginInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class LoginNetworkDataSource {
    private val _networkState = MutableLiveData<NetworkState>()
    private val _token = MutableLiveData<String>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val token: LiveData<String> get() = _token

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(LoginInterface::class.java)

    fun doLogin(email: String, password:String){
        val callGetList = api.doLogin(LoginInfo(email, password))

        _networkState.postValue(NetworkState.LOADING)
        callGetList.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("doLogin", t.printStackTrace().toString())
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful) {
                    _token.postValue((response.body().toString()))
                    _networkState.postValue(NetworkState.LOADED)
                }
                else if(response.code() == 401)
                    _networkState.postValue(NetworkState.LOGINFAIL)
                else{
                    Log.d("doLogin", response.toString())
                    _networkState.postValue(NetworkState.ERROR)
                }
            }
        })
    }
}