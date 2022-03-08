package com.zzuh.filot_shoppings_admin.ui.admin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zzuh.filot_shoppings_admin.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminBinding
    var token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        adminCheck()

        setContentView(binding.root)
        binding.userManage.setOnClickListener {
            var intent = Intent(this, UserManageActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }
        binding.productManage.setOnClickListener {
            var intent = Intent(this, ProductManageActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }
        binding.categoryManage.setOnClickListener {
            var intent = Intent(this, CategoryManageActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }
        binding.addProduct.setOnClickListener {
            var intent = Intent(this, NewProductActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun adminCheck(){
        val spToken = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        val email = spToken.getString("email", null)
        token = spToken.getString("token", null)
        if(token == null){
            Toast.makeText(this, "로그인이 필요합니다!.",Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}