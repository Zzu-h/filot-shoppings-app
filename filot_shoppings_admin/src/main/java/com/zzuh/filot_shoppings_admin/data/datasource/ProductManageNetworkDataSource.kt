package com.zzuh.filot_shoppings_admin.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zzuh.filot_shoppings_admin.data.api.BASE_URL
import com.zzuh.filot_shoppings_admin.data.api.ProductManageInterface
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.data.vo.Product
import com.zzuh.filot_shoppings_admin.data.vo.ProductDetails
import com.zzuh.filot_shoppings_admin.data.vo.ProductInfo
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
    private val _downloadProductResponse = MutableLiveData<ProductDetails>()
    private val _downloadProductListResponse = MutableLiveData<List<Product>>()

    val networkState: LiveData<NetworkState> get() = _networkState
    val downloadProductResponse: LiveData<ProductDetails> get() = _downloadProductResponse
    val downloadProductListResponse: LiveData<List<Product>> get() = _downloadProductListResponse

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
                    _downloadProductResponse.postValue(response.body() as ProductDetails)
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

    fun fetchProductAllList(){
        val callGetList = api.getAllProducts()
        _networkState.postValue(NetworkState.LOADING)

        callGetList.enqueue(object :Callback<List<Product>>{
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.d("fetchProductAllList", t.printStackTrace().toString())
                t.printStackTrace()
                _networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                Log.d("fetchProductAllList","${response.body()}")
                val item = response.body() as List<Product>
                _downloadProductListResponse.postValue(item)
                _networkState.postValue(NetworkState.LOADED)
            }
        })
    }

    fun fetchProductDetails(id: Int){
        val callGetDetails = api.getProductDetails(id)
        callGetDetails.enqueue(object :Callback<ProductDetails>{
            override fun onFailure(call: Call<ProductDetails>, t: Throwable) {
                Log.d("fetchProductDetails", t.printStackTrace().toString())
            }

            override fun onResponse(call: Call<ProductDetails>, response: Response<ProductDetails>) {
                val item = response.body() as ProductDetails
                item.images?.sort()
                _downloadProductResponse.postValue(item)
            }
        })
    }
    fun updateProduct(token: String, productId: Int, path: String, productInfo: ProductInfo){
        if(prevCheck(productInfo)) {
            _networkState.postValue(NetworkState.LOADED)
            return
        }
        // 처음 데이터와 비교, 같으면 loaded
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
                    _downloadProductResponse.postValue(response.body() as ProductDetails)
                }
                else
                    _networkState.postValue(NetworkState.ERROR)

            }
        })
    }
    private fun prevCheck(productInfo: ProductInfo): Boolean{
        return true
    }
}
