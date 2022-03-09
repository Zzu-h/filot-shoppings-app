package com.zzuh.filot_shoppings_admin.ui.admin

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings_admin.R
import com.zzuh.filot_shoppings_admin.data.repository.NetworkState
import com.zzuh.filot_shoppings_admin.data.vo.User
import com.zzuh.filot_shoppings_admin.databinding.ActivityUserManageBinding
import com.zzuh.filot_shoppings_admin.ui.viewmodel.UserManageViewModel
import com.zzuh.filot_shoppings_admin.ui.viewmodel.UserManageViewModelFactory


class UserManageActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserManageBinding
    lateinit var viewModel: UserManageViewModel

    val token:String by lazy {
        val intent = Intent()
        intent.getStringExtra("token").toString()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserManageBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this, UserManageViewModelFactory(token))
            .get(UserManageViewModel::class.java)
        viewModelSetting()
        setContentView(binding.root)
        binding.backBtn.setOnClickListener { finish() }
    }
    private fun viewModelSetting(){
        viewModel.userList.observe(this, Observer {
            if(it.isEmpty()){
                binding.noDataTv.visibility = View.VISIBLE
                binding.userCntTv.text = "0"
                return@Observer
            }
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)

            val dpi = displayMetrics.densityDpi
            val density = displayMetrics.density

            val dp = 40
            val px = (dp * density + 0.5).toInt()

            binding.noDataTv.visibility = View.GONE
            binding.userCntTv.text = "${it.size}"

            for(item in it){
                val tableRow = TableRow(this)
                tableRow.gravity = Gravity.CENTER
                tableRow.minimumHeight = px
                tableRow.isClickable = true
                tableRow.setOnClickListener { userDetailPopUp(item) }

                val emailTv = TextView(this)
                val nameTv = TextView(this)
                val classTv = TextView(this)

                emailTv.text = item.email
                emailTv.gravity = Gravity.CENTER

                nameTv.text = item.name
                nameTv.gravity = Gravity.CENTER
                nameTv.background = resources.getDrawable(R.drawable.table_cell_side_border,null)

                classTv.text = item.roles.get(0).split('_')[1]
                classTv.gravity = Gravity.CENTER

                tableRow.addView(emailTv,0)
                tableRow.addView(nameTv,1)
                tableRow.addView(classTv,2)

                binding.userManageTable.addView(tableRow)
            }
        })
        viewModel.networkState.observe(this, Observer {
            when(it){
                NetworkState.LOADING -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                    binding.loadedLayout.visibility = View.GONE
                }
                NetworkState.LOADED -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.loadedLayout.visibility = View.VISIBLE
                }
                NetworkState.ERROR -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.loadedLayout.visibility = View.GONE
                }
            }
        })
    }
    private fun userDetailPopUp(user: User){
        val popupLayout = layoutInflater.inflate(R.layout.user_manage_popup, null)
        val emailEt = popupLayout.findViewById<EditText>(R.id.user_email_item)
        val nameEt = popupLayout.findViewById<EditText>(R.id.user_name_item)
        val phoneNumberEt = popupLayout.findViewById<EditText>(R.id.phone_number_item)
        val roadAddrEt = popupLayout.findViewById<EditText>(R.id.road_address_item)
        val detailAddrEt = popupLayout.findViewById<EditText>(R.id.detail_address_item)
        val classSpinner = popupLayout.findViewById<Spinner>(R.id.class_spinner)
        val saveBtn = popupLayout.findViewById<Button>(R.id.save_btn)
        val cancelBtn = popupLayout.findViewById<Button>(R.id.cancel_btn)

        val popupWindow = PopupWindow(popupLayout, LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT, true)

        emailEt.setText(user.email)
        nameEt.setText(user.name)
        phoneNumberEt.setText(user.phoneNumber)
        roadAddrEt.setText(user.roadAddress)
        detailAddrEt.setText(user.detailAddress)

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, user.roles)
        classSpinner.adapter = adapter

        cancelBtn.setOnClickListener { popupWindow.dismiss() }
        saveBtn.setOnClickListener { popupWindow.dismiss() }

        popupWindow.showAtLocation(binding.root, Gravity.CENTER,0, 0)
    }
}