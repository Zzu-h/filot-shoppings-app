package com.zzuh.filot_shoppings.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zzuh.filot_shoppings.databinding.ActivityNewProductBinding

class NewProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewProductBinding
    private var title = "New Product"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewProductBinding.inflate(layoutInflater)
        binding.adminTitleTv.text = title

        var product = intent.getBundleExtra("product")

        setContentView(binding.root)
    }
}