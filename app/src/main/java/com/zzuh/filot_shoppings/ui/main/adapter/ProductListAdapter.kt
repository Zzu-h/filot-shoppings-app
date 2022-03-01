package com.zzuh.filot_shoppings.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zzuh.filot_shoppings.data.vo.Product
import com.zzuh.filot_shoppings.databinding.ProductListItemBinding

const val IMAGE_BASE_URL = "naver.com"

class ProductListViewHolder(val binding: ProductListItemBinding): RecyclerView.ViewHolder(binding.root)

class ProductListAdapter(private var itemList: List<Product>, val context: Context): RecyclerView.Adapter<ProductListViewHolder>() {
    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val binding = holder.binding
        val item = this.itemList[position]
        binding.nameItem.text = item.name
        binding.priceItem.text =  item.price.toString()

        val moviePosterURL: String = item.imageUrl

        Glide.with(context)
            .load(moviePosterURL)
            .into(binding.imageItem)

        binding.cardView.isClickable = true
        binding.cardView.setOnClickListener{
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id", item.id)
            intent.putExtra("thumbnail", item.imageUrl)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder
            = ProductListViewHolder(ProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    fun updateData(newData: List<Product>){
        this.itemList = newData
        notifyDataSetChanged()
    }
}