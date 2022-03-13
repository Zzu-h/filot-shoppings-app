package com.zzuh.filot_shoppings_login.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings_login.data.api.BASE_URL
import com.zzuh.filot_shoppings_login.data.api.FindUserInterface
import com.zzuh.filot_shoppings_login.data.api.JoinInterface
import com.zzuh.filot_shoppings_login.data.repository.NetworkState
import com.zzuh.filot_shoppings_login.data.vo.ChangeUserPassword
import com.zzuh.filot_shoppings_login.data.vo.JoinInfo
import com.zzuh.filot_shoppings_login.data.vo.SendEmail
import okhttp3.Cookie
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieManager

class FindUserNetworkDataSource {
    private val _emailCheck = MutableLiveData<NetworkState>()
    private val _networkState = MutableLiveData<NetworkState>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val emailCheck: LiveData<NetworkState> get() = _emailCheck

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(FindUserInterface::class.java)

    fun sendCode(email: String){
        val callGetList = api.sendCode(SendEmail(email))

        callGetList.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("sendCode", "error")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) { }
        })
    }
    fun changePassword(email: String, password: String ,code: String){
        _networkState.postValue(NetworkState.LOADING)

        val callGetList = api.changePassword(ChangeUserPassword(
            email = email,
            newPassword = password,
            code = code))

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
    fun emailCheck(email: String){
        val callGetList = api.emailCheck(email)

        _emailCheck.postValue(NetworkState.LOADING)
        callGetList.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("emailVerify", "error")
                t.printStackTrace()
                _emailCheck.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("doubleEmailCheck", response.toString())
                if(response.isSuccessful)
                    _emailCheck.postValue(NetworkState.LOADED)
                else if(response.code() == 401){
                    _emailCheck.postValue(NetworkState.EXIST_EMAIL)
                }
                else
                    _emailCheck.postValue(NetworkState.ERROR)
            }
        })
    }
}