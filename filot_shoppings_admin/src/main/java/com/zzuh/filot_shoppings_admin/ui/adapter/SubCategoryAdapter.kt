package com.zzuh.filot_shoppings_admin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zzuh.filot_shoppings_admin.data.vo.Category
import com.zzuh.filot_shoppings_admin.databinding.ManageSubCategoryListItemBinding

class SubCategoryViewHolder(val binding: ManageSubCategoryListItemBinding): RecyclerView.ViewHolder(binding.root)

class SubCategoryAdapter(private var itemList: List<Category>): RecyclerView.Adapter<SubCategoryViewHolder>() {
    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        val binding = holder.binding
        val item = this.itemList[position]
        binding.subCategoryTv.text = item.name
        binding.deleteSubCategoryBtn.setOnClickListener {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder
            = SubCategoryViewHolder(ManageSubCategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    fun updateData(newData: List<Category>){
        this.itemList = newData
        notifyDataSetChanged()
    }
}
