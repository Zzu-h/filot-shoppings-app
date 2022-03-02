package com.zzuh.filot_shoppings_login.ui.login.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzuh.filot_shoppings_login.data.repository.JoinRepository
import com.zzuh.filot_shoppings_login.data.repository.NetworkState
import com.zzuh.filot_shoppings_login.data.vo.JoinInfo

class JoinViewModel: ViewModel() {
    val joinRepository = JoinRepository()
    val networkState: LiveData<NetworkState> get() = joinRepository.networkState

    val doubleCheck = MutableLiveData<Boolean>()
    val certificateCheck = MutableLiveData<Boolean>()
    var passwordCheck = false

    var email: String? = null
    var password: String? = null
    var checkPassword: String? = null
    var detailAddress: String? = null
    var roadAddress: String? = null
    var name: String? = null
    var phoneNumber: String? = null
    var isMale: Boolean? = null

    fun doDoubleCheck(){}
    fun doCertificateCheck(){}
    fun sendCertificateCode(){}

    fun doJoin(context: Context){
        /*
         * 1. null check
         * 2. email 중복, 인증확인 check
         * 3. 비밀번호 일치 check
         */
        var msg: String? = null
        if(nullCheck())
            msg = "공란이 있습니다!"
        /*else if(doubleCheck.value == null || doubleCheck.value == false)
            msg = "email 중복확인을 해주세요!"
        else if(certificateCheck.value == null || certificateCheck.value == false)
            msg = "email 인증을 해주세요!"*/
        else if(!passwordCheck)
            msg = "비밀번호가 일치하지 않습니다!"
        else
            joinRepository.doJoin(JoinInfo(
                email = this.email!!,
                password = this.password!!,
                detailAddress = this.detailAddress!!,
                roadAddress = this.roadAddress!!,
                name = this.name!!,
                phoneNumber = this.phoneNumber!!
            ))

        if(msg != null)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
    fun checkCode(context: Context, code: String){
        if(code == ""){
            Toast.makeText(context, "코드를 입력하세요!", Toast.LENGTH_SHORT).show()
            return
        }

        joinRepository.checkCode(code)
    }
    private fun nullCheck(): Boolean{
        var isNull = false

        if(email == null || email == "") isNull = true
        if(password == null|| password == "") isNull = true
        if(detailAddress == null|| detailAddress == "") isNull = true
        if(roadAddress == null|| roadAddress == "") isNull = true
        if(name == null|| name == "") isNull = true
        if(phoneNumber == null|| phoneNumber == "") isNull = true
        if(isMale == null) isNull = true

        return isNull
    }
}