package com.zzuh.filot_shoppings.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.zzuh.filot_shoppings.data.vo.SelectedProductItem
import com.zzuh.filot_shoppings.databinding.ActivityProductDetailBinding
import com.zzuh.filot_shoppings.ui.main.viewmodel.DetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailBinding
    lateinit var detailsViewModel: DetailsViewModel

    lateinit var adapter: SelectedListAdapter

    val productId: Int by lazy {
        intent.getIntExtra("id", 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        detailsViewModel = DetailsViewModel(productId)
        adapter = SelectedListAdapter(emptyList())

        binding.selectedListRecyclerView.adapter = adapter
        setContentView(binding.root)

        detailsViewModel.product.observe(this, Observer {
            CoroutineScope(Dispatchers.Main).launch {
                binding.itemTitle.text = it.name
                binding.productPriceItem.text = "KRW ${it.price}"
                binding.deliveryPriceItem.text = "KRW ${it.deliveryPrice}"
                Glide.with(this@ProductDetailActivity)
                    .load(it.imagePath)
                    .into(binding.productImg)
            }
        })
        detailsViewModel.selectedList.observe(this, Observer { adapter.updateData(it.list as List<SelectedProductItem>) })
    }
}