package com.zzuh.filot_shoppings_admin.ui.admin


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zzuh.filot_shoppings_admin.databinding.ActivityProductManageBinding

class ProductManageActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductManageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductManageBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}