package com.zzuh.filot_shoppings_admin.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings_admin.data.api.BASE_URL
import com.zzuh.filot_shoppings_admin.data.api.ImageUploadInterface
import com.zzuh.filot_shoppings_admin.data.api.ProductManageInterface
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.data.vo.ProductDetails
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
    private val _newProductResponse = MutableLiveData<ProductDetails>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val newProductResponse: LiveData<ProductDetails> get() = _newProductResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ProductManageInterface::class.java)

    fun addNewProduct(token: String, path: String, productInfo: ProductInfo){
        val callGetList = api.addNewProduct(token = token, params = getHashMapPartBody(path, productInfo), file = getImageBody(path,"file"))
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object: Callback<ProductDetails>{
            override fun onFailure(call: Call<ProductDetails>, t: Throwable) {
                _networkState.postValue(NetworkState.ERROR)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ProductDetails>, response: Response<ProductDetails>) {
                Log.d("addNewProduct", "${response.raw()}")
                if(response.isSuccessful) {
                    _networkState.postValue(NetworkState.LOADED)
                    _newProductResponse.postValue(response.body() as ProductDetails)
                }
                else
                    _networkState.postValue(NetworkState.ERROR)

            }
        })
    }
    private fun getImageBody(path: String, name: String): MultipartBody.Part {
        val file = File(path)
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)

        return MultipartBody.Part.createFormData(name, name, requestBody)
    }
    private fun getHashMapPartBody(path: String, productInfo: ProductInfo): Map<String, RequestBody> {
        val requestBody: MutableMap<String, RequestBody> = mutableMapOf()

        requestBody["name"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.name)
        requestBody["price"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.price.toString())
        requestBody["amount"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.amount.toString())
        requestBody["size"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.size)
        requestBody["description"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.description)
        requestBody["color"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.color)
        requestBody["categoryName"] = RequestBody.create(MediaType.parse("text/plain"), productInfo.categoryName.lowercase())

        return requestBody
    }
}
