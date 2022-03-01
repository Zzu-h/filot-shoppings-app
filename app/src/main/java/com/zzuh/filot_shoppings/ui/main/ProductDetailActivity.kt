package com.zzuh.filot_shoppings.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.zzuh.filot_shoppings.R
import com.zzuh.filot_shoppings.data.vo.SelectedProductItem
import com.zzuh.filot_shoppings.databinding.ActivityProductDetailBinding
import com.zzuh.filot_shoppings.databinding.ImageListItemBinding
import com.zzuh.filot_shoppings.ui.main.viewmodel.DetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailBinding
    lateinit var detailsViewModel: DetailsViewModel

    lateinit var selectedListAdapter: SelectedListAdapter
    lateinit var imageListAdapter: ImageListAdapter

    lateinit var sizeList: List<String>
    lateinit var colorList: List<String>

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

        viewModelSetting()
    }
    private fun spinnerSetting(){
        val sizeAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, sizeList)
        val colorAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, colorList)
        binding.productSizeSpinner.adapter = sizeAdapter
        binding.productColorSpinner.adapter = colorAdapter
        binding.productSizeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("Tester", "selected $position")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("Tester", "nothing")
            }
        }
    }
    private fun viewModelSetting(){
        detailsViewModel.product.observe(this, Observer {
            CoroutineScope(Dispatchers.Main).launch {
                for(item in it.images)
                    Glide.with(this@ProductDetailActivity)
                        .load(item)

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
                colorList = it.colors
                sizeList = it.size.split(",")
                spinnerSetting()
                delay(500)
                imageListAdapter.updateData(it.images)
                binding.loadingBar.visibility = View.GONE
                binding.detailContentLayout.visibility = View.VISIBLE
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
        Log.d("Tester", item)
        Glide.with(context)
            .load(item)
            .fitCenter()
            .override(Target.SIZE_ORIGINAL)
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