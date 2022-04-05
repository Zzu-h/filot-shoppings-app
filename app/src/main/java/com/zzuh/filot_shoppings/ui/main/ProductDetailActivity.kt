package com.zzuh.filot_shoppings.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.zzuh.filot_shoppings.R
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.vo.ReviewData
import com.zzuh.filot_shoppings.data.vo.SelectedProductItem
import com.zzuh.filot_shoppings.data.vo.SelectedProductList
import com.zzuh.filot_shoppings.databinding.ActivityProductDetailBinding
import com.zzuh.filot_shoppings.databinding.ImageListItemBinding
import com.zzuh.filot_shoppings.ui.main.adapter.ReviewListAdapter
import com.zzuh.filot_shoppings.ui.main.adapter.SelectedListAdapter
import com.zzuh.filot_shoppings.ui.main.viewmodel.DetailsViewModel
import com.zzuh.filot_shoppings.ui.main.viewmodel.DetailsViewModelFactory
import com.zzuh.filot_shoppings.ui.review.NewReviewActivity
import com.zzuh.filot_shoppings.ui.review.UpdateReviewActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailBinding
    lateinit var detailsViewModel: DetailsViewModel

    lateinit var selectedListAdapter: SelectedListAdapter
    lateinit var imageListAdapter: ImageListAdapter
    lateinit var reviewListAdapter: ReviewListAdapter

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
    val email: String? by lazy { getSharedPreferences("autoLogin", Activity.MODE_PRIVATE).getString("email", null) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        detailsViewModel = ViewModelProvider(this, DetailsViewModelFactory(productId))
            .get(DetailsViewModel::class.java)
        selectedListAdapter = SelectedListAdapter(detailsViewModel, emptyList())
        imageListAdapter = ImageListAdapter(emptyList())
        reviewListAdapter = ReviewListAdapter(emptyList(), this)

        binding.selectedListRecyclerView.adapter = selectedListAdapter
        binding.reviewRv.adapter = reviewListAdapter

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

        detailsViewModel.reviewList.observe(this, Observer {
            if(it.isEmpty()){
                binding.reviewRv.isGone = true
                binding.noReviewTv.isVisible = true
            }
            else{
                binding.reviewRv.isVisible = true
                binding.noReviewTv.isGone = true
                reviewListAdapter.updateData(it)
            }
        })
        detailsViewModel.reviewNetworkState.observe(this, Observer {
            when(it){
                NetworkState.LOADING -> {
                    binding.loadingBar.isVisible = true
                    binding.detailContentLayout.isGone = true
                }
                NetworkState.LOADED -> {
                    Toast.makeText(this,"성공적으로 삭제했습니다!",Toast.LENGTH_SHORT).show()
                    finish()
                }
                NetworkState.ERROR -> {
                    Toast.makeText(this,"문제가 발생했습니다!",Toast.LENGTH_SHORT).show()
                    binding.loadingBar.isGone = true
                    binding.detailContentLayout.isVisible = true
                }
            }
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
        binding.addReviewBtn.setOnClickListener{
            val token = checkLogined()
            if(token == null){
                Toast.makeText(this,"로그인이 필요합니다!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, NewReviewActivity::class.java)
            intent.putExtra("token", token)
            intent.putExtra("productDetails", detailsViewModel.product.value!!)
            intent.putExtra("thumbnail", thumbnail)
            intent.putExtra("email", email)
            startActivity(intent)
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
    @SuppressLint("ClickableViewAccessibility")
    fun openReviewModifyPopup(reviewData: ReviewData):Boolean{
        //check is me
        val token = checkLogined()
        if (token == null || email == null || reviewData.email != email) return false

        val popupLayout = layoutInflater.inflate(R.layout.review_modify_popup, null)
        val popupWindow = PopupWindow(popupLayout, LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT, true)
        popupLayout.setOnClickListener { popupWindow.dismiss() }

        val modifyBtn = popupLayout.findViewById<Button>(R.id.modify_btn)
        val deleteBtn = popupLayout.findViewById<Button>(R.id.delete_btn)
        modifyBtn.setOnClickListener { val intent = Intent(this, UpdateReviewActivity::class.java)
            intent.putExtra("token", token)
            intent.putExtra("productDetails", detailsViewModel.product.value!!)
            intent.putExtra("review", reviewData)
            intent.putExtra("thumbnail", thumbnail)
            popupWindow.dismiss()
            startActivity(intent)
        }
        deleteBtn.setOnClickListener {
            detailsViewModel.removeReview(token, productId, reviewData.id)
            popupWindow.dismiss()
        }

        popupWindow.showAtLocation(binding.root, Gravity.CENTER,0, 0)
        return true
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