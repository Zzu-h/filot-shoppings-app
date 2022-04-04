package com.zzuh.filot_shoppings.ui.review

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.zzuh.filot_shoppings.R
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.repository.ReviewRepository
import com.zzuh.filot_shoppings.data.vo.ProductDetails
import com.zzuh.filot_shoppings.data.vo.ReviewData
import com.zzuh.filot_shoppings.databinding.ActivityNewReviewBinding
import com.zzuh.filot_shoppings_admin.data.LocalImage

class UpdateReviewActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewReviewBinding
    private val reviewRepository = ReviewRepository()

    private val productDetails by lazy {
        val temp = intent.getSerializableExtra("productDetails")
        if (temp == null) {
            Toast.makeText(this, "문제가 발생했습니다!", Toast.LENGTH_SHORT).show()
            finish()
        }
        temp as ProductDetails
    }
    private val prevReview by lazy {
        val temp = intent.getSerializableExtra("review")
        if (temp == null) {
            Toast.makeText(this, "문제가 발생했습니다!", Toast.LENGTH_SHORT).show()
            finish()
        }
        temp as ReviewData
    }
    private val token by lazy {
        val temp = intent.getStringExtra("token")
        if (temp == null) {
            Toast.makeText(this, "문제가 발생했습니다!", Toast.LENGTH_SHORT).show()
            finish()
        }
        temp
    }
    private val thumbnail: String? by lazy{
        intent.getStringExtra("thumbnail")
    }
    private val localImage = LocalImage()
    lateinit var getThumbnail: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemTitle.text = productDetails.name
        binding.ratingRb.rating = prevReview.rate
        binding.reviewItem.setText(prevReview.content)
        binding.thumbnailSetTv.text = prevReview.imageUrl
        binding.addReviewBtn.text = "리뷰 수정"
        binding.userNameItem.text = prevReview.email

        Glide.with(this)
            .load(thumbnail)
            .fitCenter()
            .override(Target.SIZE_ORIGINAL)
            .into(binding.productImg)
        Glide.with(this)
            .load(prevReview.imageUrl)
            .fitCenter()
            .override(Target.SIZE_ORIGINAL)
            .into(binding.thumbnailIv)
        getThumbnail = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                it.data?.apply {
                    val selectedImage: Uri = it.data!!.data!!
                    localImage.uri = selectedImage
                    localImage.contentResolver = contentResolver
                    localImage.fetchImage()

                    binding.thumbnailSetTv.text = localImage.fileName
                    Glide.with(this@UpdateReviewActivity)
                        .load(localImage.uri)
                        .into(binding.thumbnailIv)
                    if(localImage.isImage) localImage.isReady = true
                }
            }
        }
        btnSetting()
        reviewRepository.networkState.observe(this, Observer {
            when(it){
                NetworkState.LOADING -> {
                    binding.loadingProgressBar.isVisible = true
                    binding.contentLayout.isGone = true
                }
                NetworkState.LOADED -> {
                    Toast.makeText(this,"성공적으로 등록했습니다!",Toast.LENGTH_SHORT).show()
                    finish()
                }
                NetworkState.ERROR -> {
                    Toast.makeText(this,"문제가 발생했습니다!",Toast.LENGTH_SHORT).show()
                    binding.loadingProgressBar.isGone = true
                    binding.contentLayout.isVisible = true
                }
            }
        })
    }
    private fun btnSetting(){
        binding.backBtn.setOnClickListener { finish() }
        binding.cancelBtn.setOnClickListener { finish() }
        binding.thumbnailSetTv.setOnClickListener { getThumbnail.launch(localImage.getIntent()) }
        binding.thumbnailSetBtn.setOnClickListener {
            binding.thumbnailSetTv.setOnClickListener {}
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                binding.thumbnailSetBtn.setBackgroundColor(resources.getColor(R.color.gray_200, null))
        }
        binding.addReviewBtn.setOnClickListener {
            val rating = binding.ratingRb.rating
            val review = binding.reviewItem.text.toString()
            val thumbnail = binding.thumbnailSetTv.text.toString()
            val title = binding.reviewTitleItem.text.toString()
            if(rating < 1 || rating > 5)
                Toast.makeText(this, "별점 1~5점을 메겨주세요!", Toast.LENGTH_SHORT).show()
            else if(review.isEmpty() || thumbnail.isEmpty() || title.isEmpty())
                Toast.makeText(this, "공란이 있습니다!", Toast.LENGTH_SHORT).show()
            else
                reviewRepository.updateReview(token = token!!, prevReview.email, productId = productDetails.id.toInt(),reviewId = prevReview.id, imagePath = localImage.filePath!!, title = title, content = review, rate = rating)
        }
    }
}