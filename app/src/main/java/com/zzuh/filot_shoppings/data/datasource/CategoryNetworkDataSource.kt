package com.zzuh.filot_shoppings.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings.data.api.BASE_URL
import com.zzuh.filot_shoppings.data.api.CategoryInterface
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.vo.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class CategoryNetworkDataSource {

    private val _networkState = MutableLiveData<NetworkState>()
    private val _downloadCategoryListResponse = MutableLiveData<List<Category>>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val downloadCategoryListResponse: LiveData<List<Category>> get() = _downloadCategoryListResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(CategoryInterface::class.java)

    fun fetchCategoryList(name: String){
        val callGetList = api.getCategoryList(name)
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object :Callback<List<Category>>{
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.d("fetchCategory-error", t.printStackTrace().toString())
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if(response.isSuccessful)
                    Log.d("fetchCategoryList","${response.code()}")
                _downloadCategoryListResponse.postValue((response.body() as List<Category>))
                _networkState.postValue(NetworkState.LOADED)
            }
        })
    }
}