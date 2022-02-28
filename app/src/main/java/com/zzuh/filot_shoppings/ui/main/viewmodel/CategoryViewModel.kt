package com.zzuh.filot_shoppings.ui.main.viewmodel

import androidx.lifecycle.*
import com.zzuh.filot_shoppings.data.repository.MainCategoryRepository
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.repository.SubCategoryRepository
import com.zzuh.filot_shoppings.data.vo.Category

class CategoryViewModel(lifecycleOwner: LifecycleOwner):ViewModel() {
    var categoryName = MutableLiveData<String>()
    private var mainCategoryRepository = MainCategoryRepository(lifecycleOwner)
    private var subCategoryRepository = SubCategoryRepository(lifecycleOwner)

    var mainCategoryList = MutableLiveData<List<Category>>()
    var subCategoryList = MutableLiveData<List<Category>>()
    var mainCategoryNetworkState = MutableLiveData<NetworkState>()
    var subCategoryNetworkState = MutableLiveData<NetworkState>()

    var _isMain = MutableLiveData<Boolean?>(true)
    var isMain: Boolean? get() = _isMain.value
        set(value: Boolean?) {_isMain.postValue(value)}

    init {
        mainCategoryRepository.mainCategoryList.observe(lifecycleOwner, Observer { mainCategoryList.postValue(it) })
        mainCategoryRepository.networkState.observe(lifecycleOwner, Observer { mainCategoryNetworkState.postValue(it) })
        subCategoryRepository.subCategoryList.observe(lifecycleOwner, Observer { subCategoryList.postValue(it) })
        subCategoryRepository.networkState.observe(lifecycleOwner, Observer { subCategoryNetworkState.postValue(it) })
    }
    fun getMainCategoryList(name: String): Unit{ mainCategoryRepository.fetchMainCategoryList(name) }
    fun getSubCategoryList(name: String): Unit{ subCategoryRepository.fetchSubCategoryList(name) }

    fun setCategoryName(name: String){this.categoryName.postValue(name)}
}