package com.zzuh.filot_shoppings_login.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzuh.filot_shoppings.data.repository.JoinRepository

class JoinViewModel: ViewModel() {
    val joinRepository = JoinRepository()
    val doubleCheck = MutableLiveData<Boolean>()
    val certificateCheck = MutableLiveData<Boolean>()
    var email: String? = null
    fun doDoubleCheck(){}
    fun doCertificateCheck(){}
    fun sendCertificateCode(){}
}