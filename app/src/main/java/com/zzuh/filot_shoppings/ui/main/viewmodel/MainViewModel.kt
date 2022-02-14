package com.zzuh.filot_shoppings.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var tempData = MutableLiveData<String>()
    var _isMain = MutableLiveData<Boolean?>()
    var isMain: Boolean? get() = _isMain.value
        set(value: Boolean?) {_isMain.postValue(value)}
    init {
        _isMain.postValue(true)
    }
    fun setData(value: String): Unit = tempData.postValue(value)
}