package com.zzuh.filot_shoppings_login.ui.login.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zzuh.filot_shoppings_login.data.repository.FindUserRepository
import com.zzuh.filot_shoppings_login.data.repository.NetworkState

class FindUserViewModel: ViewModel() {
    val findUserRepository = FindUserRepository()
    val networkState: LiveData<NetworkState> get() = findUserRepository.networkState

    val emailCheck: LiveData<NetworkState> get() = findUserRepository.emailCheck
    var passwordCheck = false

    var email: String? = null
    var password: String? = null
    var checkPassword: String? = null
    var code: String? = null

    fun emailCheck(){ findUserRepository.emailCheck(this.email!!) }
    fun sendCertificateCode(){findUserRepository.sendCode(this.email!!)}

    fun changePassword(context: Context){

        var msg: String? = null
        if(nullCheck())
            msg = "공란이 있습니다!"
        else if(!passwordCheck)
            msg = "비밀번호가 일치하지 않습니다!"
        else
            findUserRepository.changePassword(
                email = this.email!!,
                password = this.password!!,
                code = this.code!!
            )

        if(msg != null)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun nullCheck(): Boolean{
        var isNull = false

        if(email == null || email == "") isNull = true
        if(password == null|| password == "") isNull = true
        if(code == null|| code == "") isNull = true

        return isNull
    }
}