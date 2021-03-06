package com.zzuh.filot_shoppings.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zzuh.filot_shoppings.data.vo.SelectedProductItem
import com.zzuh.filot_shoppings.databinding.SelectedListItemBinding
import com.zzuh.filot_shoppings.ui.main.viewmodel.DetailsViewModel

class SelectedListViewHolder(val binding: SelectedListItemBinding): RecyclerView.ViewHolder(binding.root)

class SelectedListAdapter(private val detailsViewModel: DetailsViewModel,private var itemList: List<SelectedProductItem>): RecyclerView.Adapter<SelectedListViewHolder>() {
    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: SelectedListViewHolder, position: Int) {
        val binding = holder.binding
        val item = this.itemList[position]

        binding.itemTitleTv.text = item.name
        binding.colorSizeTv.text = "- ${item.color}/${item.size}"
        binding.priceTv.text = "KRW ${item.price}"
        binding.itemCntEt.setText("${item.count}")

        binding.plusBtn.setOnClickListener {
            if(item.count<10) {
                item.count = (item.count + 1)
                val totPrice = detailsViewModel.totPrice.value
                totPrice?.apply { detailsViewModel.totPrice.postValue(totPrice + item.price) }
                binding.itemCntEt.setText("${item.count}")
            }
        }
        binding.minusBtn.setOnClickListener {
            if(item.count > 0) {
                item.count = (item.count - 1)
                val totPrice = detailsViewModel.totPrice.value
                totPrice?.apply { detailsViewModel.totPrice.postValue(totPrice - item.price) }
                binding.itemCntEt.setText("${item.count}")
            }
            if(item.count == 0){
                val list = this.itemList as MutableList
                list.removeAt(position)
                this.updateData(list)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedListViewHolder
            = SelectedListViewHolder(SelectedListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    fun updateData(newData: List<SelectedProductItem>){
        this.itemList = newData
        notifyDataSetChanged()
    }
}