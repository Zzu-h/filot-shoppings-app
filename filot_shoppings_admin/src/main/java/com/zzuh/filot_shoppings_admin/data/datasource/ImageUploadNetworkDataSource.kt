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

    var insertCount = 0
    var deleteCount = 0

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ImageUploadInterface::class.java)

    fun uploadBannerImage(token: String, path: String) {
        val callGetList = api.uploadBannerImage(token, getImageBody(path, "bannerFile"))
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                _networkState.postValue(NetworkState.ERROR)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful)
                    _networkState.postValue(NetworkState.LOADED)
                else {
                    _networkState.postValue(NetworkState.ERROR)
                    Log.d("uploadBannerImage", "${response.raw()}")
                }
            }
        })
    }

    private fun getImageBody(
        path: String,
        keyName: String,
        name: String = "bannerFile"
    ): MultipartBody.Part {
        val file = File(path)
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)

        return MultipartBody.Part.createFormData(keyName, name, requestBody)
    }

    fun uploadImageList(token: String, productId: Int, category: String, pathList: List<String>) {
        val requestBody: MutableMap<String, RequestBody> = mutableMapOf()
        requestBody["product_id"] =
            RequestBody.create(MediaType.parse("text/plain"), productId.toString())
        requestBody["category_name"] = RequestBody.create(MediaType.parse("text/plain"), category)

        val callGetList =
            api.uploadImageList(token, requestBody, getImageListBody(pathList, productId))
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                _networkState.postValue(NetworkState.ERROR)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful)
                    _networkState.postValue(NetworkState.LOADED)
                else {
                    _networkState.postValue(NetworkState.ERROR)
                    Log.d("uploadImageList", "${response.raw()}")
                }
            }
        })
    }

    private fun getImageListBody(pathList: List<String>, id: Int): ArrayList<MultipartBody.Part> {
        val list = ArrayList<MultipartBody.Part>()
        for (i in (0..pathList.lastIndex))
            list.add(getImageBody(pathList[i], "files", "${id}_${i + 1}"))
        return list
    }

    fun updateImage(token: String, productId: Int, i: Int, imagePath: String)
        = deleteImage(token, productId, i, imagePath, true)
    fun insertImage(token: String, productId: Int, index: Int, imagePath: String){
        insertCount++

        val callGetList =
            api.insertImage(token, productId, getImageBody(imagePath, "file","${productId}_${index}"))
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object: Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                insertCount--
                _networkState.postValue(NetworkState.ERROR)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                insertCount--
                if(response.isSuccessful && deleteCount == 0 && insertCount == 0)
                    _networkState.postValue(NetworkState.LOADED)
                else{
                    _networkState.postValue(NetworkState.ERROR)
                    Log.d("uploadImageList", "${response.raw()}")
                }
            }
        })
    }
    fun deleteImage(token: String, productId: Int, index: Int, imagePath: String? = null, isUpdate: Boolean = false){
        deleteCount++

        val callGetList =
            api.deleteImage(token, productId, "${productId}_${index}")
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object: Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                deleteCount--
                _networkState.postValue(NetworkState.ERROR)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                deleteCount--
                if(response.isSuccessful){
                    if(isUpdate)
                        insertImage(token, productId, index, imagePath!!)
                    else if(deleteCount == 0 && insertCount == 0)
                        _networkState.postValue(NetworkState.LOADED)
                }
                else{
                    _networkState.postValue(NetworkState.ERROR)
                    Log.d("uploadImageList", "${response.raw()}")
                }
            }
        })
    }
}
