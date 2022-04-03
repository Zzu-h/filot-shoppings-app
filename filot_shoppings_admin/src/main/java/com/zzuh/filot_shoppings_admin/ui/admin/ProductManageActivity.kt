package com.zzuh.filot_shoppings_admin.ui.admin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings_admin.R
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.databinding.ActivityProductManageBinding
import com.zzuh.filot_shoppings_admin.ui.viewmodel.ProductManageViewModel

class ProductManageActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductManageBinding
    private lateinit var viewModel: ProductManageViewModel

    val token: String by lazy {
        val intent = Intent()
        intent.getStringExtra("token").toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductManageBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ProductManageViewModel::class.java)

        viewModelSetting()
        setContentView(binding.root)
    }
    private fun viewModelSetting(){
        viewModel.networkState.observe(this, Observer {
            when(it){
                NetworkState.LOADING -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                    binding.contentLayout.visibility = View.GONE
                }
                NetworkState.LOADED -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.VISIBLE
                }
                NetworkState.ERROR -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.GONE
                }
            }
        })
        viewModel.productList.observe(this, Observer {
            if(it.isEmpty()){
                binding.noDataTv.visibility = View.VISIBLE
                binding.productCntTv.text = "0"
                return@Observer
            }
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)

            val dpi = displayMetrics.densityDpi
            val density = displayMetrics.density

            val dp = 40
            val px = dpToPx(dp, density)

            binding.noDataTv.visibility = View.GONE
            binding.productCntTv.text = "${it.size}"

            for(item in it) {
                val tableRow = TableRow(this)
                tableRow.gravity = Gravity.CENTER
                tableRow.minimumHeight = px
                tableRow.isClickable = true
                tableRow.setOnClickListener { Toast.makeText(this,"길게 누르세요",Toast.LENGTH_SHORT).show() }
                tableRow.setOnLongClickListener {
                    val intent = Intent(this, UpdateProductActivity::class.java)
                    intent.putExtra("token", token)
                    intent.putExtra("productId", item.id)
                    startActivity(intent)
                    return@setOnLongClickListener true
                }

                val checkBox = CheckBox(this)
                val nameTv = TextView(this)
                val priceTv = TextView(this)
                val productIdTv = TextView(this)

                nameTv.text = item.name
                nameTv.gravity = Gravity.CENTER
                nameTv.background = resources.getDrawable(R.drawable.table_cell_side_border, null)

                priceTv.text = item.price.toString()
                priceTv.gravity = Gravity.CENTER
                priceTv.background = resources.getDrawable(R.drawable.table_cell_side_border, null)

                productIdTv.text = item.id.toString()
                productIdTv.gravity = Gravity.CENTER

                tableRow.addView(checkBox, 0)
                tableRow.addView(nameTv, 1)
                tableRow.addView(priceTv, 2)
                tableRow.addView(productIdTv, 3)

                binding.manageTable.addView(tableRow)
            }
        })
    }
    private fun dpToPx(dp: Int, density: Float): Int = (dp * density + 0.5).toInt()
}