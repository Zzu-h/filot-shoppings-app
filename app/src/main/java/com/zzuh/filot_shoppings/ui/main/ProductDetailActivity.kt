package com.zzuh.filot_shoppings.ui.main

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.zzuh.filot_shoppings.R
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.vo.Product
import com.zzuh.filot_shoppings.data.vo.SelectedProductItem
import com.zzuh.filot_shoppings.data.vo.SelectedProductList
import com.zzuh.filot_shoppings.databinding.ActivityProductDetailBinding
import com.zzuh.filot_shoppings.databinding.ImageListItemBinding
import com.zzuh.filot_shoppings.ui.main.adapter.SelectedListAdapter
import com.zzuh.filot_shoppings.ui.main.viewmodel.CartViewModel
import com.zzuh.filot_shoppings.ui.main.viewmodel.CartViewModelFactory
import com.zzuh.filot_shoppings.ui.main.viewmodel.DetailsViewModel
import com.zzuh.filot_shoppings.ui.main.viewmodel.DetailsViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailBinding
    lateinit var detailsViewModel: DetailsViewModel

    lateinit var selectedListAdapter: SelectedListAdapter
    lateinit var imageListAdapter: ImageListAdapter

    lateinit var sizeList: MutableList<String>
    lateinit var colorList: MutableList<String>

    var selectedItemSizeIdx: Int = 0
    var selectedItemColorIdx: Int = 0

    val productId: Int by lazy {
        intent.getIntExtra("id", 0)
    }
    val thumbnail: String? by lazy{
        intent.getStringExtra("thumbnail")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        detailsViewModel = ViewModelProvider(this, DetailsViewModelFactory(productId))
            .get(DetailsViewModel::class.java)
        selectedListAdapter = SelectedListAdapter(detailsViewModel, emptyList())
        imageListAdapter = ImageListAdapter(emptyList())

        binding.selectedListRecyclerView.adapter = selectedListAdapter
        //binding.descriptionImageRv.adapter = imageListAdapter

        setContentView(binding.root)

        viewModelSetting()
        buttonSetting()
    }
    private fun spinnerSetting(){
        sizeList.add(0, "")
        colorList.add(0, "")
        val sizeAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, sizeList)
        val colorAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, colorList)
        binding.productSizeSpinner.adapter = sizeAdapter
        binding.productColorSpinner.adapter = colorAdapter
        binding.productSizeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItemSizeIdx = position
                checkAllOptionSelected()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("Tester", "nothing")
            }
        }
        binding.productColorSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItemColorIdx = position
                checkAllOptionSelected()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("Tester", "nothing")
            }
        }
    }
    private fun checkAllOptionSelected(){
        if(selectedItemColorIdx == 0 || selectedItemSizeIdx == 0) return
        val list = detailsViewModel.selectedList.value?.list as MutableList
        list.add(SelectedProductItem(
            id = productId,
            name = detailsViewModel.product.value!!.name,
            color = colorList[selectedItemColorIdx],
            size = sizeList[selectedItemSizeIdx].toInt(),
            price = detailsViewModel.product.value!!.price,
        ))
        detailsViewModel.selectedList.postValue( SelectedProductList(list) )
        var tot = detailsViewModel.totPrice.value
        tot?.apply { tot += detailsViewModel.product.value!!.price }
        detailsViewModel.totPrice.postValue(tot)

        selectedItemColorIdx = 0
        selectedItemSizeIdx = 0
    }
    private fun viewModelSetting(){
        detailsViewModel.product.observe(this, Observer {
            CoroutineScope(Dispatchers.Main).launch {
                for(index in 0..it.images.lastIndex)
                    imageLoad(index, it.images[index])

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
                colorList = it.colors as MutableList<String>
                val size = it.size + ","
                sizeList = size.split(",") as MutableList<String>
                sizeList.removeLast()
                spinnerSetting()
                delay(500)
                /*imageListAdapter.updateData(it.images)*/
                binding.loadingBar.visibility = View.GONE
                binding.detailContentLayout.visibility = View.VISIBLE
            }
        })
        detailsViewModel.selectedList.postValue(SelectedProductList(emptyList<SelectedProductItem>().toMutableList()))
        detailsViewModel.selectedList.observe(this, Observer { selectedListAdapter.updateData(it.list as List<SelectedProductItem>) })
        detailsViewModel.totPrice.observe(this, Observer {
            Log.d("Total Price", "$it")
            binding.totPriceTv.text = "KRW $it"
        })
    }
    private fun buttonSetting(){
        binding.backBtn.setOnClickListener { finish() }
        binding.cartBtn.setOnClickListener {
            if(detailsViewModel.selectedList.value == null || detailsViewModel.selectedList.value!!.list.isEmpty()) {
                Toast.makeText(this,"상품을 선택해 주세요!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val token = checkLogined()
            if(token == null){
                Toast.makeText(this,"로그인이 필요합니다!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.d("basket","start")
            for(item in detailsViewModel.selectedList.value!!.list)
                detailsViewModel.putProductBasket(token, item)
            detailsViewModel.networkState.observe(this, Observer {
                when(it){
                    NetworkState.LOADED -> finish()
                    NetworkState.ERROR -> Toast.makeText(this,"상품 물량 부족 또는 네트워크 상태 이상!",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    private fun checkLogined(): String?{
        return getSharedPreferences(
            "autoLogin",
            Activity.MODE_PRIVATE)
            .getString("token", null)
    }
    private fun imageLoad(index: Int, url: String){
        val imageView by lazy {
            when(index){
                0 -> return@lazy binding.imageListItem1
                1 -> return@lazy binding.imageListItem2
                2 -> return@lazy binding.imageListItem3
                3 -> return@lazy binding.imageListItem4
                4 -> return@lazy binding.imageListItem5
                else -> return@lazy binding.imageListItem1
            }
        }
        Glide.with(this)
            .load(url)
            .fitCenter()
            .override(Target.SIZE_ORIGINAL)
            .into(imageView.descriptionIv)
        imageView.root.visibility = View.VISIBLE
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