package com.zzuh.filot_shoppings.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings.data.api.BASE_URL
import com.zzuh.filot_shoppings.data.api.ReviewInterface
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.vo.ReviewData
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

class ReviewNetworkDataSource {

    private val _networkState = MutableLiveData<NetworkState>()
    private val _downloadReviewListResponse = MutableLiveData<List<ReviewData>>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val downloadReviewListResponse: LiveData<List<ReviewData>> get() = _downloadReviewListResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ReviewInterface::class.java)

    private fun getImageBody(
        path: String,
        keyName: String,
        name: String = "null"
    ): MultipartBody.Part {
        val file = File(path)
        val requestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)

        return MultipartBody.Part.createFormData(keyName, name, requestBody)
    }
    private fun getTextMediaType(): MediaType? = "text/plain".toMediaTypeOrNull()

    fun fetchReviewList(productId: Int){
        val callGetList = api.getReviews(productId)

        callGetList.enqueue(object :Callback<List<ReviewData>>{
            override fun onFailure(call: Call<List<ReviewData>>, t: Throwable) {
                Log.d("fetchCartList-error", t.printStackTrace().toString())
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<List<ReviewData>>, response: Response<List<ReviewData>>) {
                if(!response.isSuccessful){
                    _networkState.postValue(NetworkState.ERROR)
                    Log.d("fetchReviewList","${response.raw()}")
                    return
                }
                _downloadReviewListResponse.postValue(response.body() as List<ReviewData>)
            }
        })
    }

    fun removeReview(token: String, productId: Int, reviewId: Int){
        val callGetList = api.removeReview(token, productId, reviewId)
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object :Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(!response.isSuccessful){
                    _networkState.postValue(NetworkState.ERROR)
                    Log.d("removeReview","${response.raw()}")
                    return
                }
                _networkState.postValue(NetworkState.LOADED)
            }
        })
    }
    fun addReview(token: String, email: String, productId: Int, imagePath: String, title: String, content: String, rate: Float){
        val requestBody: MutableMap<String, RequestBody> = mutableMapOf()
        requestBody["title"] = RequestBody.create(getTextMediaType(), title)
        requestBody["content"] = RequestBody.create(getTextMediaType(), content)
        requestBody["rate"] = RequestBody.create(getTextMediaType(), rate.toString())
        val tokesValue = token.substring(0, 10)
        val callGetList = api.addReview(token, productId, requestBody, getImageBody(imagePath, "file", "${productId}_${email}.jpg"))
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object :Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(!response.isSuccessful){
                    _networkState.postValue(NetworkState.ERROR)
                    Log.d("addReview","${response.raw()}")
                    return
                }
                _networkState.postValue(NetworkState.LOADED)
            }
        })
    }
    fun updateReview(token: String,email: String, productId: Int, reviewId: Int, imagePath: String, title: String, content: String, rate: Float){
        val requestBody: MutableMap<String, RequestBody> = mutableMapOf()
        requestBody["title"] = RequestBody.create(getTextMediaType(), title)
        requestBody["content"] = RequestBody.create(getTextMediaType(), content)
        requestBody["rate"] = RequestBody.create(getTextMediaType(), rate.toString())
        val tokesValue = token.substring(0, 10)
        val callGetList = api.updateReview(token, productId, reviewId, requestBody, getImageBody(imagePath, "key", "${productId}_${email}.jpg"))
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object :Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(!response.isSuccessful){
                    _networkState.postValue(NetworkState.ERROR)
                    Log.d("updateReview","${response.raw()}")
                    return
                }
                _networkState.postValue(NetworkState.LOADED)
            }
        })
    }
}