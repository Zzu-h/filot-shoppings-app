package com.zzuh.filot_shoppings_admin.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings_admin.data.api.BASE_URL
import com.zzuh.filot_shoppings_admin.data.api.ImageUploadInterface
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
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

class ImageUploadNetworkDataSource {

    private val _networkState = MutableLiveData<NetworkState>()
    private val _downloadUserListResponse = MutableLiveData<List<User>>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val downloadUserListResponse: LiveData<List<User>> get() = _downloadUserListResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ImageUploadInterface::class.java)

    fun uploadBannerImage(token: String, path: String ){
        val callGetList = api.uploadBannerImage(token, getImageBody(path, "bannerFile"))
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object: Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                _networkState.postValue(NetworkState.ERROR)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful)
                    _networkState.postValue(NetworkState.LOADED)
                else{
                    _networkState.postValue(NetworkState.ERROR)
                    Log.d("uploadBannerImage", "${response.raw()}")
                }
            }
        })
    }

    private fun getImageBody(path: String, name: String): MultipartBody.Part {
        val file = File(path)
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)

        return MultipartBody.Part.createFormData(name, name, requestBody)
    }

    fun uploadImageList(token: String, productId: Int, category: String, pathList: List<String> ){
        val requestBody: MutableMap<String, RequestBody> = mutableMapOf()
        requestBody["product_id"] = RequestBody.create(MediaType.parse("text/plain"), productId.toString())
        requestBody["category_name"] = RequestBody.create(MediaType.parse("text/plain"), category)

        val callGetList = api.uploadImageList(token, requestBody, getImageListBody(pathList, productId))
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object: Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                _networkState.postValue(NetworkState.ERROR)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful)
                    _networkState.postValue(NetworkState.LOADED)
                else{
                    _networkState.postValue(NetworkState.ERROR)
                    Log.d("uploadImageList", "${response.raw()}")
                }
            }
        })
    }

    private fun getImageListBody(pathList: List<String>, id: Int): List<MultipartBody.Part>{
        val list = mutableListOf<MultipartBody.Part>()
        for(i in (0..pathList.size)) list.add(getImageBody(pathList[i], "${id}_${i+1}"))
        return list
    }
}
