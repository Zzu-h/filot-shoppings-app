package com.zzuh.filot_shoppings_admin.ui.admin


import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zzuh.filot_shoppings_admin.R
import com.zzuh.filot_shoppings_admin.data.LocalImage
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.databinding.ActivityNewProductBinding
import com.zzuh.filot_shoppings_admin.ui.adapter.DetailImageAdapter
import com.zzuh.filot_shoppings_admin.ui.viewmodel.NewProductViewModel
import com.zzuh.filot_shoppings_admin.ui.viewmodel.NewProductViewModelFactory

class NewProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewProductBinding
    lateinit var newProductViewModel: NewProductViewModel
    lateinit var getThumbnail: ActivityResultLauncher<Intent>

    lateinit var getDetailImage_1: ActivityResultLauncher<Intent>
    lateinit var getDetailImage_2: ActivityResultLauncher<Intent>
    lateinit var getDetailImage_3: ActivityResultLauncher<Intent>
    lateinit var getDetailImage_4: ActivityResultLauncher<Intent>
    lateinit var getDetailImage_5: ActivityResultLauncher<Intent>

    lateinit var localImage: LocalImage

    private val title = "New Product"
    private val token: String by lazy {
        val intent = Intent()
        intent.getStringExtra("token").toString()
    }
    private val categoryList = mutableListOf("")
    private val imagePathList = mutableListOf("",  "", "",  "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewProductBinding.inflate(layoutInflater)
        binding.adminTitleTv.text = title


        var product = intent.getBundleExtra("product")

        setContentView(binding.root)
        localImageSetting()
        viewModelSetting()
        btnSetting()
        spinnerSetting()
    }
    private fun viewModelSetting(){
        newProductViewModel = ViewModelProvider(this, NewProductViewModelFactory(token))
            .get(NewProductViewModel::class.java)

        newProductViewModel.categoryNetworkState.observe(this, Observer {
            when(it){
                NetworkState.LOADING -> {
                    binding.newProductLayout.isGone = true
                    binding.loadingProgressBar.isVisible = true
                }
                NetworkState.LOADED -> {
                    binding.newProductLayout.isVisible = true
                    binding.loadingProgressBar.isGone = true
                }
                NetworkState.ERROR -> {
                    Toast.makeText(this, "오류가 발생했습니다!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        })
        newProductViewModel.categoryList.observe(this, Observer {
            /*
            data parsing and spinner 등록
             */
            for(item in it){
                categoryList.add(item.name)
                item.children.forEach { category -> categoryList.add(category.name) }
            }

            val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, categoryList)
            binding.categoryItem.adapter = adapter
        })
        newProductViewModel.productManageNetworkState.observe(this, Observer {
            when(it){
                NetworkState.LOADING -> {
                    binding.newProductLayout.isGone = true
                    binding.loadingProgressBar.isVisible = true
                    binding.newProductImageLayout.isGone = true
                }
                NetworkState.ERROR -> {
                    binding.newProductLayout.isVisible = true
                    binding.loadingProgressBar.isGone = true
                    binding.newProductImageLayout.isGone = true
                }
                NetworkState.LOADED -> {
                    binding.newProductLayout.isGone = true
                    binding.loadingProgressBar.isGone = true
                    binding.newProductImageLayout.isVisible = true

                    localImage.clear()
                }
            }
        })
        newProductViewModel.newProductResponse.observe(this, Observer {
            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.thumbnailIv)
            newProductViewModel.productId = it.id.toInt()
            binding.productTitleTv.text = it.name
            binding.amountTv.text = it.amount.toString()
            binding.categoryTv.text = newProductViewModel.categoryName
            binding.sizeListTv.text = it.size
            binding.productPriceTv.text = "KRW ${it.price}"
        })
    }
    private fun spinnerSetting(){
        binding.categoryItem.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newProductViewModel.categoryName = categoryList[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("Tester", "nothing")
            }
        }
    }
    private fun btnSetting(){
        binding.backBtn.setOnClickListener { finish() }
        binding.cancelBtn.setOnClickListener { finish() }
        binding.addNewProductBtn.setOnClickListener {
            newProductViewModel.name = binding.productTitleItem.text.toString()
            newProductViewModel.size = getSizeString()
            newProductViewModel.description = binding.productDetailsItem.text.toString()
            newProductViewModel.color = binding.productColorsItem.text.toString()
            newProductViewModel.categoryName
            newProductViewModel.path = localImage.filePath
            newProductViewModel.price = binding.productPriceItem.text.toString().toInt()
            newProductViewModel.amount = binding.amountItem.text.toString().toInt()

            newProductViewModel.addProduct(this)
        }
        binding.thumbnailSetBtn.setOnClickListener {
            if(localImage.isReady) {
                newProductViewModel.thumbnailConfirm = true
                binding.thumbnailSetTv.isClickable = false
                binding.thumbnailSetBtn.isClickable = false
                binding.thumbnailSetBtn.setBackgroundColor(Color.GRAY)
            }
            else Toast.makeText(this, "이미지를 불러와 주세요!", Toast.LENGTH_SHORT).show()
        }
        binding.thumbnailSetTv.setOnClickListener { getThumbnail.launch(localImage.getIntent()) }
        binding.checkbox85Item.setOnClickListener { binding.checkbox85Item.toggle() }
        binding.checkbox90Item.setOnClickListener { binding.checkbox90Item.toggle() }
        binding.checkbox95Item.setOnClickListener { binding.checkbox95Item.toggle() }
        binding.checkbox100Item.setOnClickListener { binding.checkbox100Item.toggle() }
        binding.checkbox105Item.setOnClickListener { binding.checkbox105Item.toggle() }
        binding.checkbox110Item.setOnClickListener { binding.checkbox110Item.toggle() }

        binding.imageListItem1.deletePathBtn.setOnClickListener { deleteLocalImage(0) }
        binding.imageListItem2.deletePathBtn.setOnClickListener { deleteLocalImage(1) }
        binding.imageListItem3.deletePathBtn.setOnClickListener { deleteLocalImage(2) }
        binding.imageListItem4.deletePathBtn.setOnClickListener { deleteLocalImage(3) }
        binding.imageListItem5.deletePathBtn.setOnClickListener { deleteLocalImage(4) }

        binding.imageListItem1.imagePathTv.setOnClickListener { getDetailImage_1.launch(localImage.getIntent()) }
        binding.imageListItem2.imagePathTv.setOnClickListener { getDetailImage_2.launch(localImage.getIntent()) }
        binding.imageListItem3.imagePathTv.setOnClickListener { getDetailImage_3.launch(localImage.getIntent()) }
        binding.imageListItem4.imagePathTv.setOnClickListener { getDetailImage_4.launch(localImage.getIntent()) }
        binding.imageListItem5.imagePathTv.setOnClickListener { getDetailImage_5.launch(localImage.getIntent()) }

        binding.newProductUploadBtn.setOnClickListener {
            val list = mutableListOf<String>()
            imagePathList.forEach { it -> if(it.isNotEmpty()) list.add(it) }
            if(list.isEmpty()) Toast.makeText(this,"이미지가 적어도 1개가 필요합니다!", Toast.LENGTH_SHORT).show()
            else newProductViewModel.uploadImage(list)
        }
        binding.cancel2Btn.setOnClickListener { finish() }
    }
    private fun localImageSetting(){
        localImage = LocalImage()

        getThumbnail = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                it.data?.apply {
                    val selectedImage: Uri = it.data!!.data!!
                    localImage.uri = selectedImage
                    localImage.contentResolver = contentResolver
                    localImage.fetchImage()

                    binding.thumbnailSetTv.text = localImage.fileName
                    if(localImage.isImage) localImage.isReady = true
                }
            }
        }
        getDetailImage_1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ setLocalImageToList(0, it) }
        getDetailImage_2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ setLocalImageToList(1, it) }
        getDetailImage_3 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ setLocalImageToList(2, it) }
        getDetailImage_4 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ setLocalImageToList(3, it) }
        getDetailImage_5 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ setLocalImageToList(4, it) }

    }
    private fun deleteLocalImage(index: Int){
        //if (imagePathList.get(index).isEmpty()) return

        when(index){
            0 -> binding.imageListItem1.imagePathTv.text = "image"
            1 -> binding.imageListItem2.imagePathTv.text = "image"
            2 -> binding.imageListItem3.imagePathTv.text = "image"
            3 -> binding.imageListItem4.imagePathTv.text = "image"
            4 -> binding.imageListItem5.imagePathTv.text = "image"
        }
        imagePathList[index] = ""
    }
    private fun setLocalImageToList(index: Int, it: ActivityResult){
        if(it.resultCode == RESULT_OK){
            it.data?.apply {
                val selectedImage: Uri = it.data!!.data!!
                localImage.uri = selectedImage
                localImage.contentResolver = contentResolver
                localImage.fetchImage()

                when(index){
                    0 -> binding.imageListItem1.imagePathTv.text = localImage.fileName
                    1 -> binding.imageListItem2.imagePathTv.text = localImage.fileName
                    2 -> binding.imageListItem3.imagePathTv.text = localImage.fileName
                    3 -> binding.imageListItem4.imagePathTv.text = localImage.fileName
                    4 -> binding.imageListItem5.imagePathTv.text = localImage.fileName
                }
                imagePathList[0] = localImage.filePath!!

                localImage.clear()
            }
        }
    }

    private fun getSizeString(): String{
        var sizeStr = ""

        if(binding.checkbox85Item.isChecked) sizeStr += ",85"
        if(binding.checkbox90Item.isChecked) sizeStr += ",90"
        if(binding.checkbox95Item.isChecked) sizeStr += ",95"
        if(binding.checkbox100Item.isChecked) sizeStr += ",100"
        if(binding.checkbox105Item.isChecked) sizeStr += ",105"
        if(binding.checkbox110Item.isChecked) sizeStr += ",110"
        if(!binding.sizeEt.text.isNullOrBlank()) sizeStr += ",${binding.sizeEt.text.toString()}"

        if(sizeStr.isNotEmpty()) sizeStr = sizeStr.substring(1)

        return sizeStr
    }
}