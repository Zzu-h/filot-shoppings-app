package com.zzuh.filot_shoppings_login.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings_login.data.repository.NetworkState
import com.zzuh.filot_shoppings_login.databinding.ActivityFindUserBinding
import com.zzuh.filot_shoppings_login.ui.login.viewmodel.FindUserViewModel

class FindUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityFindUserBinding
    lateinit var findUserViewModel: FindUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindUserBinding.inflate(layoutInflater)
        findUserViewModel = ViewModelProvider(this)
            .get(FindUserViewModel::class.java)

        setContentView(binding.root)

        btnSetting()
        viewModelSetting()
        initializeEditText()
    }

    private fun btnSetting(){
        binding.cancelBtn.setOnClickListener { finish() }
        binding.emailBtn.setOnClickListener { emailValidCheck(binding.emailEt.text.toString()) }
        binding.reSendBtn.setOnClickListener { findUserViewModel.sendCertificateCode() }
        binding.changePasswordBtn.setOnClickListener { findUserViewModel.changePassword(this) }
    }
    private fun viewModelSetting(){
        findUserViewModel.emailCheck.observe(this, Observer {
            when(it){
                NetworkState.LOADED -> {
                    binding.changePasswordBtn.isClickable = false

                    binding.changePasswordLayout.isGone = true
                    binding.emailLayout.isVisible = true
                    binding.progressBar.isGone = true
                }
                NetworkState.LOADING -> {
                    binding.changePasswordBtn.isClickable = false

                    binding.changePasswordLayout.isGone = true
                    binding.emailLayout.isGone = true
                    binding.progressBar.isVisible = true
                }
                NetworkState.EXIST_EMAIL -> {
                    findUserViewModel.sendCertificateCode()
                    binding.changePasswordBtn.isClickable = true

                    binding.changePasswordLayout.isVisible = true
                    binding.emailLayout.isVisible = true
                    binding.progressBar.isGone = true
                }
                NetworkState.ERROR -> {
                    binding.txtError.isVisible = true
                    binding.changePasswordBtn.isClickable = false

                    binding.changePasswordLayout.isGone = true
                    binding.emailLayout.isGone = true
                    binding.progressBar.isGone = true
                }
            }
        })
        findUserViewModel.networkState.observe(this, Observer {
            when(it){
                NetworkState.LOADED -> {
                    Toast.makeText(this,"변경에 성공했습니다!", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
                NetworkState.LOADING -> {
                    binding.changePasswordBtn.isClickable = false

                    binding.changePasswordLayout.isGone = true
                    binding.emailLayout.isGone = true
                    binding.progressBar.isVisible = true
                }
                NetworkState.ERROR -> {
                    binding.changePasswordBtn.isClickable = true
                    Toast.makeText(this,"다시 시도해주세요!", Toast.LENGTH_SHORT)
                        .show()

                    binding.changePasswordLayout.isVisible = true
                    binding.emailLayout.isVisible = true
                    binding.progressBar.isGone = true
                }
            }
        })
    }
    private fun initializeEditText(){
        binding.emailEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(email: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(email: Editable?) {
                findUserViewModel.email = email?.toString()
            }
        })
        binding.passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(password: Editable?) {
                findUserViewModel.password = password?.toString()
            }
        })
        binding.checkPasswordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(password: Editable?) {
                findUserViewModel.passwordCheck = (findUserViewModel.password == password?.toString())
                findUserViewModel.checkPassword = password?.toString()
                if(!findUserViewModel.passwordCheck)
                    binding.checkPasswordEt.error = "비밀번호가 다릅니다!"
            }
        })
    }

    private fun emailValidCheck(email: String){
        var pattern = android.util.Patterns.EMAIL_ADDRESS
        if(email == "") Toast.makeText(this, "email을 입력해 주세요", Toast.LENGTH_SHORT).show()
        else if(!pattern.matcher(email).matches()) Toast.makeText(this, "email 형식이 아닙니다!", Toast.LENGTH_SHORT).show()
        else findUserViewModel.emailCheck()
    }
}