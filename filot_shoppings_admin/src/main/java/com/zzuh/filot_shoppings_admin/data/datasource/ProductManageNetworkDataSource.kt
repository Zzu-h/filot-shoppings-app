package com.zzuh.filot_shoppings_admin.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings_admin.data.api.BASE_URL
import com.zzuh.filot_shoppings_admin.data.api.ImageUploadInterface
import com.zzuh.filot_shoppings_admin.data.api.ProductManageInterface
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.data.vo.ProductInfo
import com.zzuh.filot_shoppings_admin.data.vo.User
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

class ProductManageNetworkDataSource {

    private val _networkState = MutableLiveData<NetworkState>()
    private val _downloadUserListResponse = MutableLiveData<List<User>>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val downloadUserListResponse: LiveData<List<User>> get() = _downloadUserListResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ProductManageInterface::class.java)

    fun addNewProduct(token: String, path: String, productInfo: ProductInfo){
        val callGetList = api.addNewProduct(token = token, params = getHashMapPartBody(path, productInfo))
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object: Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                _networkState.postValue(NetworkState.ERROR)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("addNewProduct", "${response.raw()}")
                if(response.isSuccessful)
                    _networkState.postValue(NetworkState.LOADED)
                else
                    _networkState.postValue(NetworkState.ERROR)

            }
        })
    }
    private fun getHashMapPartBody(path: String, productInfo: ProductInfo): Map<String, RequestBody> {
        val requestBody: MutableMap<String, RequestBody> = mutableMapOf()

        requestBody["name"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.name)
        requestBody["price"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.price.toString())
        requestBody["amount"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.amount.toString())
        requestBody["size"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.size)
        requestBody["description"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.description)
        requestBody["color"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.color)
        requestBody["categoryName"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.categoryName)
        requestBody["file"] = RequestBody.create(MediaType.parse("image/*"), File(path))

        return requestBody
    }
}
