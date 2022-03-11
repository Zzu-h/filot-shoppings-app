package com.zzuh.filot_shoppings_admin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zzuh.filot_shoppings_admin.data.vo.MainCategory
import com.zzuh.filot_shoppings_admin.databinding.ManageMainCategoryListItemBinding

class MainCategoryViewHolder(val binding: ManageMainCategoryListItemBinding): RecyclerView.ViewHolder(binding.root){
     val subCategoryAdapter = SubCategoryAdapter(emptyList())
    init {
        binding.subCategoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = subCategoryAdapter
        }
    }
}

class MainCategoryAdapter(private var itemList: List<MainCategory>): RecyclerView.Adapter<MainCategoryViewHolder>() {
    override fun getItemCount(): Int = itemList.size
    private lateinit var context: Context

    override fun onBindViewHolder(holder: MainCategoryViewHolder, position: Int) {
        val binding = holder.binding
        val item = this.itemList[position]
        binding.mainCategoryTv.text = item.name
        holder.subCategoryAdapter.updateData(item.children)
        holder.subCategoryAdapter.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoryViewHolder {
        context = parent.context
        return MainCategoryViewHolder(ManageMainCategoryListItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    fun updateData(newData: List<MainCategory>){
        this.itemList = newData
        notifyDataSetChanged()
    }
}
