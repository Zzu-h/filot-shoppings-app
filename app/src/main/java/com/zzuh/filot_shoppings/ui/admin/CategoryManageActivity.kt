package com.zzuh.filot_shoppings.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zzuh.filot_shoppings.databinding.ActivityCategoryManageBinding

class CategoryManageActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryManageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryManageBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}