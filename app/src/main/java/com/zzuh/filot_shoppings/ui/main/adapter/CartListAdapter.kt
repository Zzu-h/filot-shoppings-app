package com.zzuh.filot_shoppings.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zzuh.filot_shoppings.data.vo.BasketItem
import com.zzuh.filot_shoppings.data.vo.SelectedProductItem
import com.zzuh.filot_shoppings.databinding.CartListItemBinding
import com.zzuh.filot_shoppings.ui.main.ProductDetailActivity
import com.zzuh.filot_shoppings.ui.main.viewmodel.CartViewModel

class CartListViewHolder(val binding: CartListItemBinding): RecyclerView.ViewHolder(binding.root)

class CartListAdapter(private val context: Context,private val cartViewModel: CartViewModel, private var itemList: List<BasketItem>): RecyclerView.Adapter<CartListViewHolder>() {
    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        val binding = holder.binding
        val item = this.itemList[position]

        binding.itemTitle.text = item.productName
        binding.optionItem.text = "- ${item.productOption}"
        binding.productPriceItem.text = "KRW ${item.productPrice}"
        binding.itemCntTv.setText("${item.selectedCount}")
        Glide.with(context)
            .load(item.productUrl)
            .into(binding.productImg)
        binding.productImg.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id", item.productId)
            intent.putExtra("thumbnail", item.productUrl)
            context.startActivity(intent)
        }
        binding.itemTitle.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id", item.productId)
            intent.putExtra("thumbnail", item.productUrl)
            context.startActivity(intent)
        }


        binding.plusBtn.setOnClickListener {
            if(item.selectedCount<10) {
                item.selectedCount = (item.selectedCount +1)
                cartViewModel.updateProductCnt(item.id, item.selectedCount)
                val totPrice = cartViewModel.totPrice.value
                totPrice?.apply { cartViewModel.totPrice.postValue(totPrice + item.productPrice) }
                binding.itemCntTv.setText("${item.selectedCount}")
            }
        }
        binding.minusBtn.setOnClickListener {
            if(item.selectedCount > 0) {
                item.selectedCount = (item.selectedCount -1)
                cartViewModel.updateProductCnt(item.id, item.selectedCount)
                val totPrice = cartViewModel.totPrice.value
                binding.itemCntTv.setText("${item.selectedCount}")
                totPrice?.apply {
                    if(item.selectedCount == 0){
                        val list = itemList as MutableList
                        //Log.d("bkId/prId", "${item.id}/${item.productId}")
                        cartViewModel.deleteProductBasket(item.id)
                        list.removeAt(position)
                        updateData(list)
                    }
                    else
                        cartViewModel.totPrice.postValue(totPrice - item.productPrice)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder
            = CartListViewHolder(CartListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    fun updateData(newData: List<BasketItem>){
        this.itemList = newData
        notifyDataSetChanged()
    }
}