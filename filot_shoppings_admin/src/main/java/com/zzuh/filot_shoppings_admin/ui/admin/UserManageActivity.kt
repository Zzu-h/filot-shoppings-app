package com.zzuh.filot_shoppings_admin.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings_admin.databinding.ActivityUserManageBinding
import com.zzuh.filot_shoppings_admin.ui.viewmodel.UserManageViewModel
import com.zzuh.filot_shoppings_admin.ui.viewmodel.UserManageViewModelFactory

class UserManageActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserManageBinding
    lateinit var viewModel: UserManageViewModel
    val token:String by lazy {
        val intent = Intent()
        intent.getStringExtra("token").toString()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserManageBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this, UserManageViewModelFactory(token))
            .get(UserManageViewModel::class.java)
        setContentView(binding.root)
        viewModel.fetchUserList()
        binding.backBtn.setOnClickListener { finish() }
    }
}