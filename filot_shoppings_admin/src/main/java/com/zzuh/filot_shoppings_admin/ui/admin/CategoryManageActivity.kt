package com.zzuh.filot_shoppings_admin.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zzuh.filot_shoppings_admin.databinding.ActivityCategoryManageBinding

class CategoryManageActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryManageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryManageBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}