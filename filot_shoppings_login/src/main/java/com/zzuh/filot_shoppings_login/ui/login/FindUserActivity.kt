package com.zzuh.filot_shoppings_login.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zzuh.filot_shoppings_login.databinding.ActivityFindUserBinding

class FindUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityFindUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindUserBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.cancelBtn.setOnClickListener { finish() }
        binding.authNumberBtn.setOnClickListener {
            binding.emailEv.isEnabled = false
            binding.phoneNumberEv.isEnabled = false
            binding.authNumberEv.isEnabled = false
            binding.joinBtn.isClickable = true
            binding.changePasswordLayout.visibility = View.VISIBLE
        }
    }
}