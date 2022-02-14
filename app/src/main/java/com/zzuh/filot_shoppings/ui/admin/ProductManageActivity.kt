package com.zzuh.filot_shoppings.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zzuh.filot_shoppings.databinding.ActivityProductManageBinding

class ProductManageActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductManageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductManageBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}