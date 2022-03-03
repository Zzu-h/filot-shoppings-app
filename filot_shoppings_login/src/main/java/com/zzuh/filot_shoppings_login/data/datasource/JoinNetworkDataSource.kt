package com.zzuh.filot_shoppings_login.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings_login.data.api.BASE_URL
import com.zzuh.filot_shoppings_login.data.api.JoinInterface
import com.zzuh.filot_shoppings_login.data.repository.NetworkState
import com.zzuh.filot_shoppings_login.data.vo.JoinInfo
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

class JoinNetworkDataSource {
    private val _emailDoubleCheck = MutableLiveData<NetworkState>()
    private val _networkState = MutableLiveData<NetworkState>()
    private var sessionCookie: String? = null

    val networkState: LiveData<NetworkState> get() = _networkState
    val emailDoubleCheck: LiveData<NetworkState> get() = _emailDoubleCheck

    val okHttpClient = OkHttpClient
        .Builder()
        .cookieJar(JavaNetCookieJar(CookieManager()))
        .build()
    private val retrofit = Retrofit.Builder()
        //.client(okHttpClient)
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
                Log.d("emailVerify",response.headers().toString())
                sessionCookie = response.headers().get("Set-Cookie")
                Log.d("emailVerify",sessionCookie.toString())
            }
        })
    }
    fun checkCode(code: String){
        _networkState.postValue(NetworkState.LOADING)
        if(sessionCookie == null) {
            _networkState.postValue(NetworkState.ERROR)
            return
        }
        val callGetList = api.verifyCheck(cookie = sessionCookie!!, code)
        Log.d("Cookie", sessionCookie!!)
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
        val callGetList = api.emailDoubleCheck(email)
        _emailDoubleCheck.postValue(NetworkState.LOADING)
        callGetList.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("emailVerify", "error")
                t.printStackTrace()
                _emailDoubleCheck.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("doubleEmailCheck", response.toString())
                if(response.isSuccessful)
                    _emailDoubleCheck.postValue(NetworkState.LOADED)
                else if(response.code() == 401){
                    _emailDoubleCheck.postValue(NetworkState.LOGINFAIL)
                }
                else
                    _emailDoubleCheck.postValue(NetworkState.ERROR)
            }
        })
    }
}