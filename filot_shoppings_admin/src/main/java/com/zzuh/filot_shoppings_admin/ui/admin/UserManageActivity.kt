package com.zzuh.filot_shoppings_admin.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zzuh.filot_shoppings_admin.databinding.ActivityUserManageBinding

class UserManageActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserManageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserManageBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}