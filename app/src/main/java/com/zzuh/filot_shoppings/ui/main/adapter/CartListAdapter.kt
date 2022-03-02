package com.zzuh.filot_shoppings.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zzuh.filot_shoppings.data.vo.Product
import com.zzuh.filot_shoppings.data.vo.SelectedProductItem
import com.zzuh.filot_shoppings.databinding.CartListItemBinding
import com.zzuh.filot_shoppings.databinding.ProductListItemBinding
import com.zzuh.filot_shoppings.databinding.SelectedListItemBinding

class CartListViewHolder(val binding: CartListItemBinding): RecyclerView.ViewHolder(binding.root)

class CartListAdapter(private var itemList: List<SelectedProductItem>): RecyclerView.Adapter<CartListViewHolder>() {
    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        val binding = holder.binding
        val item = this.itemList[position]

        binding.itemTitle.text = item.name
        binding.optionItem.text = "- ${item.color}/${item.size}"
        binding.productPriceItem.text = "KRW ${item.price}"
        binding.itemCntEt.setText("${item.count}")

        binding.plusBtn.setOnClickListener {
            if(item.count<10) {
                item.count.inc()
                binding.itemCntEt.setText("${item.count}")
            }
        }
        binding.plusBtn.setOnClickListener {
            if(item.count > 0) {
                item.count.dec()
                binding.itemCntEt.setText("${item.count}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder
            = CartListViewHolder(CartListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    fun updateData(newData: List<SelectedProductItem>){
        this.itemList = newData
        notifyDataSetChanged()
    }
}