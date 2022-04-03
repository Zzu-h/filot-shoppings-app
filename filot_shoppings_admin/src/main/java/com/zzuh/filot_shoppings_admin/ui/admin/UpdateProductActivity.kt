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
import com.zzuh.filot_shoppings_admin.ui.viewmodel.UpdateProductViewModel
import com.zzuh.filot_shoppings_admin.ui.viewmodel.UpdateProductViewModelFactory
/**
 * 1. id를 통해서 서버로부터 product Detail 정보를 받아온다. api: https://filot-shopping.herokuapp.com/product/{product_id}
 * 2. 첫번째 화면에 데이터를 뿌린다.(단, image list 정렬 후 화면에 뿌림)
 * 3. 갱신 버튼을 눌렀을 때 기존 정보와 비교하며, 달라진 점이 있을 때만 업데이트를 한다.
 * 4. 이미지 화면에서 이미지 layout에 del, add 기능 구현 -- TODO List 영상참고
 * 5. 모든 처리가 완료되었을 때 finish
 */

class UpdateProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewProductBinding
    lateinit var updateProductViewModel: UpdateProductViewModel
    lateinit var getThumbnail: ActivityResultLauncher<Intent>

    lateinit var getDetailImage_1: ActivityResultLauncher<Intent>
    lateinit var getDetailImage_2: ActivityResultLauncher<Intent>
    lateinit var getDetailImage_3: ActivityResultLauncher<Intent>
    lateinit var getDetailImage_4: ActivityResultLauncher<Intent>
    lateinit var getDetailImage_5: ActivityResultLauncher<Intent>

    lateinit var localImage: LocalImage

    private val title = "Update Product"
    private val token: String by lazy {
        intent.getStringExtra("token").toString()
    }
    private val productId: Int by lazy {
        intent.getIntExtra("productId",0)
    }
    private val categoryList = mutableListOf("")
    private val imagePathList = mutableListOf("",  "", "",  "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewProductBinding.inflate(layoutInflater)
        binding.adminTitleTv.text = title

        setContentView(binding.root)
        localImageSetting()
        spinnerSetting()
        viewModelSetting()
        btnSetting()
    }
    private fun viewModelSetting(){
        updateProductViewModel = ViewModelProvider(this, UpdateProductViewModelFactory(token, productId))
            .get(UpdateProductViewModel::class.java)
        updateProductViewModel.categoryNetworkState.observe(this, Observer {
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
        updateProductViewModel.categoryList.observe(this, Observer {
            for(item in it){
                categoryList.add(item.name)
                item.children.forEach { category -> categoryList.add(category.name) }
            }

            val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, categoryList)
            binding.categoryItem.adapter = adapter
        })
            // product update network state
        updateProductViewModel.productManageNetworkState.observe(this, Observer {
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
                    binding.productTitleTv.text = updateProductViewModel.name
                    binding.amountTv.text = updateProductViewModel.amount.toString()
                    binding.categoryTv.text = updateProductViewModel.categoryName
                    binding.sizeListTv.text = updateProductViewModel.size
                    binding.productPriceTv.text = "KRW ${updateProductViewModel.price}"

                    binding.newProductLayout.isGone = true
                    binding.loadingProgressBar.isGone = true
                    binding.newProductImageLayout.isVisible = true

                    localImage.clear()
                }
            }
        })
        updateProductViewModel.productDetails.observe(this, Observer {
            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.thumbnailIv)
            Log.d("product-item",it.toString())
            // 첫번째 화면
            binding.productTitleItem.setText(it.name)
            binding.productPriceItem.setText(it.price.toString())
            binding.categoryItem.setSelection(categoryList.indexOf(""))
            val color = it.colors.toString()
            binding.productColorsItem.setText(color.substring(1,color.lastIndex))
            binding.amountItem.setText(it.amount.toString())
            binding.thumbnailSetTv.text = it.imageUrl.toString()
            binding.productDetailsItem.setText(it.description.toString())
            binding.addNewProductBtn.text = "변경하기"
            binding.thumbnailSetBtn.isGone = true
            val sizeList = it.size.split(',')
            if(sizeList.contains("85")) binding.checkbox85Item.toggle()
            if(sizeList.contains("90")) binding.checkbox90Item.toggle()
            if(sizeList.contains("95")) binding.checkbox95Item.toggle()
            if(sizeList.contains("100")) binding.checkbox100Item.toggle()
            if(sizeList.contains("105")) binding.checkbox105Item.toggle()
            if(sizeList.contains("110")) binding.checkbox110Item.toggle()

            // 두번째 화면 building
            binding.productTitleTv.text = updateProductViewModel.name
            binding.amountTv.text = updateProductViewModel.amount.toString()
            binding.categoryTv.text = updateProductViewModel.categoryName
            binding.sizeListTv.text = updateProductViewModel.size
            binding.productPriceTv.text = "KRW ${updateProductViewModel.price}"
            //binding.newProductUploadBtn.isGone = true
            it.images?.apply {
                for(i in 0..this.lastIndex){
                    val fileName = getImageName(this[i])
                    imagePathList[i] = this[i]
                    when(i) {
                        0 -> binding.imageListItem1.imagePathTv.text = fileName
                        1 -> binding.imageListItem2.imagePathTv.text = fileName
                        2 -> binding.imageListItem3.imagePathTv.text = fileName
                        3 -> binding.imageListItem4.imagePathTv.text = fileName
                        4 -> binding.imageListItem5.imagePathTv.text = fileName
                    }
                }
            }
        })
    }
    private fun spinnerSetting(){
        binding.categoryItem.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateProductViewModel.categoryName = categoryList[position]
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
            updateProductViewModel.name = binding.productTitleItem.text.toString()
            updateProductViewModel.size = getSizeString()
            updateProductViewModel.description = binding.productDetailsItem.text.toString()
            updateProductViewModel.color = binding.productColorsItem.text.toString()
            updateProductViewModel.categoryName
            updateProductViewModel.path = localImage.filePath
            updateProductViewModel.price = binding.productPriceItem.text.toString().toInt()
            updateProductViewModel.amount = binding.amountItem.text.toString().toInt()

            updateProductViewModel.updateProduct(this)
        }
        binding.thumbnailSetBtn.setOnClickListener {
            if(localImage.isReady) {
                updateProductViewModel.thumbnailConfirm = true
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
            val check = mutableListOf(false, false, false, false, false)
            if (binding.imageListItem5.imagePathTv.text != "image") check[0] = true
            if (binding.imageListItem4.imagePathTv.text != "image") check[1] = true
            if (binding.imageListItem3.imagePathTv.text != "image") check[2] = true
            if (binding.imageListItem2.imagePathTv.text != "image") check[3] = true
            if (binding.imageListItem1.imagePathTv.text != "image") check[4] = true
            var lastIdx = 0
            for (i in 0..4) if (check[i]) {
                lastIdx = i
                break
            }
            for (i in lastIdx..4) if (!check[i]) {
                Toast.makeText(this, "이미지에 공란이 있습니다! 메꿔주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            updateProductViewModel.updateImage(imagePathList)
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
                    0 -> {
                        binding.imageListItem1.imagePathTv.text = localImage.fileName
                        imagePathList[0] = localImage.filePath!!
                    }
                    1 -> {
                        binding.imageListItem2.imagePathTv.text = localImage.fileName
                        imagePathList[1] = localImage.filePath!!
                    }
                    2 -> {
                        binding.imageListItem3.imagePathTv.text = localImage.fileName
                        imagePathList[2] = localImage.filePath!!
                    }
                    3 -> {
                        binding.imageListItem4.imagePathTv.text = localImage.fileName
                        imagePathList[3] = localImage.filePath!!
                    }
                    4 -> {
                        binding.imageListItem5.imagePathTv.text = localImage.fileName
                        imagePathList[4] = localImage.filePath!!
                    }
                }

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
    private fun getImageName(name: String): String {
        val list = name.split('/')
        return list[list.lastIndex]
    }
}