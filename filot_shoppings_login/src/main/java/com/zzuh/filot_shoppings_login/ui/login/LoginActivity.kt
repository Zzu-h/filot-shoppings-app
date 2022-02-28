package com.zzuh.filot_shoppings.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.zzuh.filot_shoppings.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        }
    }
}