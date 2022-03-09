package com.zzuh.filot_shoppings_admin.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings_admin.data.api.BASE_URL
import com.zzuh.filot_shoppings_admin.data.api.UserManageInterface
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.data.vo.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class UserManageNetworkDataSource {

    private val _networkState = MutableLiveData<NetworkState>()
    private val _downloadUserListResponse = MutableLiveData<List<User>>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val downloadUserListResponse: LiveData<List<User>> get() = _downloadUserListResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(UserManageInterface::class.java)

    fun fetchUserList(token: String){
        val callGetList = api.getUserList(token)
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object :Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("fetchUserList-error", t.printStackTrace().toString())
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                Log.d("fetchUserList","${response.body()}")
                val item = response.body() as List<User>
                _downloadUserListResponse.postValue(item)
                _networkState.postValue(NetworkState.LOADED)
            }
        })
    }
}