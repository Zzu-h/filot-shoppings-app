package com.zzuh.filot_shoppings.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zzuh.filot_shoppings.data.vo.Product
import com.zzuh.filot_shoppings.data.vo.ReviewData
import com.zzuh.filot_shoppings.databinding.ProductListItemBinding
import com.zzuh.filot_shoppings.databinding.ReviewListItemBinding
import com.zzuh.filot_shoppings.ui.main.ProductDetailActivity
import com.zzuh.filot_shoppings_admin.R
import java.sql.Date
import java.text.DateFormat
import java.util.*

class ReviewListViewHolder(val binding: ReviewListItemBinding): RecyclerView.ViewHolder(binding.root)

class ReviewListAdapter(private var itemList: List<ReviewData>, val context: Context): RecyclerView.Adapter<ReviewListViewHolder>() {
    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {
        val binding = holder.binding
        val item = this.itemList[position]
        val email = (context as ProductDetailActivity).email
        if(item.email == email && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M))
            binding.root.setBackgroundColor(context.resources.getColor(com.zzuh.filot_shoppings.R.color.gray_800, null))

        binding.userNameTv.text = item.userName
        if(item.email == email && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) binding.userNameTv.setTextColor(context.resources.getColor(com.zzuh.filot_shoppings.R.color.white, null))
        binding.reviewContentTv.text =  item.content
        if(item.email == email && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) binding.reviewContentTv.setTextColor(context.resources.getColor(com.zzuh.filot_shoppings.R.color.white, null))
        binding.ratingRb.rating = item.rate
        binding.reviewTitleTv.text = item.title
        if(item.email == email && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) binding.reviewTitleTv.setTextColor(context.resources.getColor(com.zzuh.filot_shoppings.R.color.white, null))

        val date = item.date.split('T')[0]
        binding.createDateTv.text = date
        if(item.email == email && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) binding.createDateTv.setTextColor(context.resources.getColor(com.zzuh.filot_shoppings.R.color.white, null))

        val reviewImageURL: String = item.imageUrl
        Glide.with(context)
            .load(reviewImageURL)
            .into(binding.reviewImageIv)
        binding.root.setOnLongClickListener { (context as ProductDetailActivity).openReviewModifyPopup(item) }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewListViewHolder
            = ReviewListViewHolder(ReviewListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    fun updateData(newData: List<ReviewData>){
        this.itemList = newData
        notifyDataSetChanged()
    }
}