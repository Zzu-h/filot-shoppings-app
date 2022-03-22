package com.zzuh.filot_shoppings_admin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zzuh.filot_shoppings_admin.databinding.NewProductImageListItemBinding

class DetailImageViewHolder(val binding: NewProductImageListItemBinding): RecyclerView.ViewHolder(binding.root)

class DetailImageAdapter(var itemList: MutableList<String>): RecyclerView.Adapter<DetailImageViewHolder>() {
    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
        val binding = holder.binding
        val item = this.itemList[position]
        binding.imagePathTv.text = item
        //binding.deletePathBtn.setOnClickListener { removeData(position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder
            = DetailImageViewHolder(
        NewProductImageListItemBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent,
            false)
    )
    fun updateData(newData: List<String>){
        this.itemList = newData as MutableList<String>
        notifyDataSetChanged()
    }

    fun addData(data: String) {
        itemList.add(data)
        notifyDataSetChanged()
    }
    fun removeData(position: Int){
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }
}
