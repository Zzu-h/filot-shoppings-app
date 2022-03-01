package com.zzuh.filot_shoppings.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.zzuh.filot_shoppings.data.vo.SelectedProductItem
import com.zzuh.filot_shoppings.databinding.ActivityProductDetailBinding
import com.zzuh.filot_shoppings.databinding.ImageListItemBinding
import com.zzuh.filot_shoppings.ui.main.viewmodel.DetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailBinding
    lateinit var detailsViewModel: DetailsViewModel

    lateinit var selectedListAdapter: SelectedListAdapter
    lateinit var imageListAdapter: ImageListAdapter

    val productId: Int by lazy {
        intent.getIntExtra("id", 0)
    }
    val thumbnail: String? by lazy{
        intent.getStringExtra("thumbnail")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        detailsViewModel = DetailsViewModel(productId)
                /*ViewModelProvider(this)
            .get(DetailsViewModel::class.java)*/
        selectedListAdapter = SelectedListAdapter(emptyList())
        imageListAdapter = ImageListAdapter(emptyList())

        binding.selectedListRecyclerView.adapter = selectedListAdapter
        binding.descriptionImageRv.adapter = imageListAdapter

        setContentView(binding.root)
        binding.backBtn.setOnClickListener { finish() }

        detailsViewModel.product.observe(this, Observer {
            CoroutineScope(Dispatchers.Main).launch {
                binding.itemTitle.text = it.name
                binding.productPriceItem.text = "KRW ${it.price}"
                it.description?.let {
                    binding.descriptionTv.text = it
                    binding.descriptionTv.visibility = View.VISIBLE
                }
                it.deliveryPrice?.let { binding.deliveryPriceItem.text = "KRW ${it}" }
                thumbnail?.let {
                    Glide.with(this@ProductDetailActivity)
                        .load(thumbnail)
                        .into(binding.productImg)
                }
                imageListAdapter.updateData(it.images)
            }
        })
        detailsViewModel.selectedList.observe(this, Observer { selectedListAdapter.updateData(it.list as List<SelectedProductItem>) })
    }
}

class ImageListViewHolder(val binding: ImageListItemBinding): RecyclerView.ViewHolder(binding.root)
class ImageListAdapter(private var itemList: List<String>): RecyclerView.Adapter<ImageListViewHolder>() {
    lateinit var context: Context
    override fun getItemCount(): Int = itemList.size
    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        val binding = holder.binding
        val item = this.itemList[position]
        Log.d("Tester", item+"${itemList.size}")
        Glide.with(context)
            .load(item)
            .into(binding.descriptionIv)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        this.context = parent.context
        return ImageListViewHolder( ImageListItemBinding.inflate(LayoutInflater.from(context), parent,false) )
    }

    fun updateData(newData: List<String>) {
        this.itemList = newData
        notifyDataSetChanged()
    }
}