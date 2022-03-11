package com.zzuh.filot_shoppings_admin.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings_admin.data.api.BASE_URL
import com.zzuh.filot_shoppings_admin.data.api.CategoryInterface
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.data.vo.Category
import com.zzuh.filot_shoppings_admin.data.vo.MainCategory
import com.zzuh.filot_shoppings_admin.data.vo.NewCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class CategoryNetworkDataSource {

    private val _networkState = MutableLiveData<NetworkState>()
    private val _downloadCategoryListResponse = MutableLiveData<List<MainCategory>>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val downloadCategoryListResponse: LiveData<List<MainCategory>> get() = _downloadCategoryListResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(CategoryInterface::class.java)

    fun fetchCategoryAllList(){
        val callGetList = api.getCategoryAllList()
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object :Callback<List<MainCategory>>{
            override fun onFailure(call: Call<List<MainCategory>>, t: Throwable) {
                Log.d("fetchCategory-error", t.printStackTrace().toString())
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<List<MainCategory>>, response: Response<List<MainCategory>>) {
                Log.d("fetchCategoryList", response.body().toString())
                _downloadCategoryListResponse.postValue((response.body() as List<MainCategory>))
                _networkState.postValue(NetworkState.LOADED)
            }
        })
    }
    fun addSubCategory(token: String, parent: String, child: String){
        val callGetList = api.addSubCategory(token, NewCategory(parent, child))
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object :Callback<MainCategory>{
            override fun onFailure(call: Call<MainCategory>, t: Throwable) {
                Log.d("addSubCategory-error", t.printStackTrace().toString())
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<MainCategory>, response: Response<MainCategory>) {
                if(response.isSuccessful) _networkState.postValue(NetworkState.LOADED)
                else _networkState.postValue(NetworkState.ERROR)
                Log.d("addSubCategory", response.raw().toString())
            }
        })
    }
}