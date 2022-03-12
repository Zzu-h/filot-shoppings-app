package com.zzuh.filot_shoppings_admin.ui.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings_admin.data.LocalImage
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.databinding.ActivityAdminBinding
import com.zzuh.filot_shoppings_admin.ui.viewmodel.ImageUploadViewModel
import com.zzuh.filot_shoppings_admin.ui.viewmodel.ImageUploadViewModelFactory
import java.io.FileNotFoundException


class AdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminBinding
    lateinit var imageUploadViewModel: ImageUploadViewModel
    var token: String? = null
    val localImage = LocalImage()

    private lateinit var getImage: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        adminCheck()
        imageUploadViewModel = ViewModelProvider(this, ImageUploadViewModelFactory(token!!))
            .get(ImageUploadViewModel::class.java)
        getImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                it.data?.apply {
                    val selectedImage: Uri = it.data!!.data!!
                    localImage.uri = selectedImage
                    localImage.contentResolver = contentResolver
                    localImage.fetchImage()

                    binding.bannerSetTv.text = localImage.fileName
                    if(localImage.isImage) localImage.isReady = true
                }
            }
        }
        setContentView(binding.root)
        buttonSetting()
        imageUploadViewModel.networkState.observe(this, Observer {
            when(it){
                NetworkState.LOADING -> {
                    binding.bannerSetBtn.isClickable = false
                    binding.bannerSetTv.isClickable = false
                }
                NetworkState.LOADED -> {
                    Toast.makeText(this, "배너 이미지가 변경되었습니다!", Toast.LENGTH_SHORT).show()
                    binding.bannerSetBtn.isClickable = true
                    binding.bannerSetTv.isClickable = true
                    binding.bannerSetTv.text = ""
                    localImage.clear()
                }
                NetworkState.ERROR -> {
                    Toast.makeText(this, "이미지 변경에 실패했습니다!", Toast.LENGTH_SHORT).show()
                    binding.bannerSetBtn.isClickable = true
                    binding.bannerSetTv.isClickable = true
                }
            }
        })
    }
    private fun buttonSetting(){
        binding.userManage.setOnClickListener {
            var intent = Intent(this, UserManageActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }
        binding.productManage.setOnClickListener {
            var intent = Intent(this, ProductManageActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }
        binding.categoryManage.setOnClickListener {
            var intent = Intent(this, CategoryManageActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }
        binding.addProduct.setOnClickListener {
            var intent = Intent(this, NewProductActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }
        binding.backBtn.setOnClickListener { finish() }
        binding.bannerSetTv.setOnClickListener { getImage.launch(localImage.getIntent()) }
        binding.bannerSetBtn.setOnClickListener {
            if(localImage.isReady)
                imageUploadViewModel.uploadBannerImage(localImage.filePath!!)
            else
                Toast.makeText(this,"image is not ready", Toast.LENGTH_SHORT).show()
        }
    }

    private fun adminCheck(){
        val spToken = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        val email = spToken.getString("email", null)
        token = spToken.getString("token", null)
        if(token == null){
            Toast.makeText(this, "로그인이 필요합니다!.",Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}