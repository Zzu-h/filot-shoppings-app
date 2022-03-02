package com.zzuh.filot_shoppings_login.ui.login

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.lifecycle.Observer
import com.zzuh.filot_shoppings_login.R
import com.zzuh.filot_shoppings_login.data.repository.LoginRepository
import com.zzuh.filot_shoppings_login.data.repository.NetworkState
import com.zzuh.filot_shoppings_login.databinding.ActivityLoginBinding
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var loginRepository: LoginRepository
    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Tester", "OnCreate LoginActivity")
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginRepository = LoginRepository()
        initializeButton()
        initializePopupWindow()
        loginRepository.token.observe(this, Observer { this.token = it })
    }
    private fun initializeButton(){
        binding.backBtn.setOnClickListener { finish() }
        binding.joinTv.setOnClickListener{
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
        binding.findTv.setOnClickListener{
            val intent = Intent(this, FindUserActivity::class.java)
            startActivity(intent)
        }
        binding.loginBtn.setOnClickListener {
            var email = binding.emailEt.text.toString()
            var password = binding.passwordEt.text.toString()
            if(email == "") {
                binding.emailEt.setError("email을 입력해 주세요")
                return@setOnClickListener
            }
            if(password == ""){
                binding.passwordEt.setError("password를 입력해 주세요")
                return@setOnClickListener
            }
            loginRepository.doLogin(email, password)
        }
    }
    private fun initializePopupWindow(){
        val popupLayout = layoutInflater.inflate(R.layout.login_popup, null)
        val loadingLayout = popupLayout.findViewById<LinearLayout>(R.id.loading_login)
        val failLayout = popupLayout.findViewById<LinearLayout>(R.id.login_failed)
        val popupWindow = PopupWindow(popupLayout, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT, true)

        loginRepository.networkState.observe(this, Observer {
            when(it){
                NetworkState.LOADING -> {
                    loadingLayout.visibility = View.VISIBLE
                    failLayout.visibility = View.GONE
                    popupWindow.showAtLocation(binding.root,Gravity.CENTER,0, 0)
                }
                NetworkState.LOGINFAIL -> {
                    if(popupWindow.isShowing) {
                        loadingLayout.visibility = View.GONE
                        failLayout.visibility = View.VISIBLE
                        failLayout.findViewById<Button>(R.id.check_btn).setOnClickListener {
                            popupWindow.dismiss()
                        }
                    }
                }
                NetworkState.LOADED -> {
                    if(popupWindow.isShowing) {
                        popupWindow.dismiss()
                        val spToken = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
                        val spEditor = spToken.edit()
                        spEditor.putString("token", this.token)
                        spEditor.putString("email", binding.emailEt.text.toString())
                        spEditor.apply()
                        finish()
                    }
                }
            }
        })
    }
}