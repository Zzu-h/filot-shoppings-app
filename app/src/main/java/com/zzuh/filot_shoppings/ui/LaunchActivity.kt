package com.zzuh.filot_shoppings.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.zzuh.filot_shoppings.R
import com.zzuh.filot_shoppings.data.api.BASE_URL
import com.zzuh.filot_shoppings.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

class LaunchActivity : AppCompatActivity() {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(BannerInterface::class.java)

    lateinit var baseUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        setLaunchScreenTimeOut()

    }
    private fun getBannerUrl(){
        val callGetUrl = api.getBannerUrl()
        callGetUrl.enqueue(object: Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
                errorFinish()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("getBaseUrl", "${response.raw()}")
                if(!response.isSuccessful || response.body() == null)
                    errorFinish()
                else {
                    baseUrl = response.body() as String
                    startMainActivity()
                }
            }
        })
    }
    private fun errorFinish(){
        Toast.makeText(this@LaunchActivity, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
        finish()
    }
    private fun setLaunchScreenTimeOut(){
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            getBannerUrl()
        }
    }
    private fun startMainActivity(){
        // Login 상태인 확인
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("banner", baseUrl)
        startActivity(intent)
        finish()
    }
    interface BannerInterface {
        @GET("/admin/banners")
        fun getBannerUrl(): Call<String>
    }
}
