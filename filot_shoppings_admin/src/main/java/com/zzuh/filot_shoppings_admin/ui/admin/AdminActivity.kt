package com.zzuh.filot_shoppings_admin.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zzuh.filot_shoppings_admin.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.userManage.setOnClickListener {
            var intent = Intent(this, UserManageActivity::class.java)
            startActivity(intent)
        }
        binding.productManage.setOnClickListener {
            var intent = Intent(this, ProductManageActivity::class.java)
            startActivity(intent)
        }
        binding.categoryManage.setOnClickListener {
            var intent = Intent(this, CategoryManageActivity::class.java)
            startActivity(intent)
        }
        binding.addProduct.setOnClickListener {
            var intent = Intent(this, NewProductActivity::class.java)
            startActivity(intent)
        }
        binding.backBtn.setOnClickListener { finish() }
    }
}