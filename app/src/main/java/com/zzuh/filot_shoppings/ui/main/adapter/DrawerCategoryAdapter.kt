package com.zzuh.filot_shoppings.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zzuh.filot_shoppings.data.vo.Category
import com.zzuh.filot_shoppings.data.vo.Product
import com.zzuh.filot_shoppings.databinding.DrawerCategoryListItemBinding
import com.zzuh.filot_shoppings.databinding.ProductListItemBinding
import com.zzuh.filot_shoppings.ui.main.ProductDetailActivity

class DrawerCategoryViewHolder(val binding: DrawerCategoryListItemBinding): RecyclerView.ViewHolder(binding.root)

class DrawerCategoryAdapter(private var itemList: List<Category>, private val onChangeCategory: OnChangeCategory): RecyclerView.Adapter<DrawerCategoryViewHolder>() {

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: DrawerCategoryViewHolder, position: Int) {
        val binding = holder.binding
        val item = this.itemList[position].name
        binding.categoryTv.text = item
        binding.categoryTv.isClickable = true
        binding.categoryTv.setOnClickListener{
            onChangeCategory.changeFragment(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerCategoryViewHolder
            = DrawerCategoryViewHolder(DrawerCategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    fun updateData(newData: List<Category>){
        this.itemList = newData
        notifyDataSetChanged()
    }
    interface OnChangeCategory{
        fun changeFragment(name: String)
    }
}