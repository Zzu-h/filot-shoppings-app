package com.zzuh.filot_shoppings_login.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.zzuh.filot_shoppings_login.R
import com.zzuh.filot_shoppings_login.data.repository.NetworkState
import com.zzuh.filot_shoppings_login.databinding.ActivityJoinBinding
import com.zzuh.filot_shoppings_login.ui.login.viewmodel.JoinViewModel

class JoinActivity : AppCompatActivity() {
    lateinit var joinViewModel: JoinViewModel

    lateinit var transaction: FragmentTransaction
    lateinit var fragmentManager: FragmentManager
    lateinit var binding: ActivityJoinBinding

    lateinit var basicInfoFragment: BasicInfoFragment
    lateinit var addInfoFragment: AddInfoFragment

    lateinit var basicInfoTab: TabLayout.Tab
    lateinit var addInfoTab: TabLayout.Tab

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        joinViewModel = ViewModelProvider(this)
            .get(JoinViewModel::class.java)

        initFragmentSetting()
        initButtonSetting()
        initializePopupWindow()

        setContentView(binding.root)
    }
    private fun initFragmentSetting(){
        basicInfoFragment = BasicInfoFragment(joinViewModel)
        addInfoFragment = AddInfoFragment(joinViewModel)

        basicInfoTab = binding.joinTabLayout.newTab()
        addInfoTab = binding.joinTabLayout.newTab()
        basicInfoTab.text = "기본 정보"
        addInfoTab.text = "추가 정보"

        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()

        binding.joinTabLayout.addTab(basicInfoTab)
        binding.joinTabLayout.addTab(addInfoTab)

        transaction.add(R.id.join_fragment_layout, basicInfoFragment)
        transaction.commit()
        binding.joinTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab != null){
                    transaction = fragmentManager.beginTransaction()
                    if(tab.text == "기본 정보"){
                        binding.joinBtn.text = "다음"
                        transaction.replace(R.id.join_fragment_layout, basicInfoFragment)
                    }
                    else if(tab.text == "추가 정보"){
                        binding.joinBtn.text = "회원가입"
                        transaction.replace(R.id.join_fragment_layout, addInfoFragment)
                    }
                    transaction.commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("onTabUnselected", "unSelected")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("onTabReselected","touch")
            }
        })
    }
    private fun initButtonSetting(){
        binding.joinBtn.setOnClickListener {
            if(fragmentManager.findFragmentById(R.id.join_fragment_layout) is BasicInfoFragment){
                binding.joinTabLayout.selectTab(addInfoTab)
                transaction = fragmentManager.beginTransaction()
                binding.joinBtn.text = "회원가입"
                transaction.replace(R.id.join_fragment_layout, addInfoFragment)
                transaction.commit()
            }
            else{
                Log.d("setOnClickListener","Join!!")
                joinViewModel.doJoin(this)
            }
        }
        binding.cancelBtn.setOnClickListener {
            // repository delete
            finish()
        }
    }
    private fun initializePopupWindow(){
        val popupLayout = layoutInflater.inflate(R.layout.join_popup, null)
        val loadingLayout = popupLayout.findViewById<LinearLayout>(R.id.loading_join)
        val failLayout = popupLayout.findViewById<LinearLayout>(R.id.join_failed)
        val verifyLayout = popupLayout.findViewById<LinearLayout>(R.id.verify_layout)

        val popupWindow = PopupWindow(popupLayout, LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT, true)

        joinViewModel.networkState.observe(this, Observer {
            when(it){
                NetworkState.LOADING -> {
                    if(popupWindow.isShowing) {
                        loadingLayout.visibility = View.VISIBLE
                        failLayout.visibility = View.GONE
                        verifyLayout.visibility = View.GONE
                    }
                }
                NetworkState.ERROR -> {
                    if(popupWindow.isShowing) {
                        loadingLayout.visibility = View.GONE
                        failLayout.visibility = View.VISIBLE
                        verifyLayout.visibility = View.GONE
                        failLayout.findViewById<Button>(R.id.check_btn).setOnClickListener {
                            verifyLayout.findViewById<EditText>(R.id.name_et).setText("")
                            popupWindow.dismiss()
                        }
                    }
                }
                NetworkState.LOADED -> {
                    if(popupWindow.isShowing) {
                        popupWindow.dismiss()
                        Toast.makeText(this,"회원가입에 성공했습니다!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
                NetworkState.CHECKINGCODE -> {
                    popupWindow.showAtLocation(binding.root, Gravity.CENTER,0, 0)

                    verifyLayout.visibility = View.VISIBLE
                    loadingLayout.visibility = View.GONE
                    failLayout.visibility = View.GONE

                    val codeEt = verifyLayout.findViewById<EditText>(R.id.name_et)
                    val reSendBtn = verifyLayout.findViewById<Button>(R.id.verify_resend_btn)
                    val checkBtn = verifyLayout.findViewById<Button>(R.id.verify_check_btn)
                    val cancelBtn = verifyLayout.findViewById<Button>(R.id.cancel_btn)

                    reSendBtn.setOnClickListener { joinViewModel.doJoin(this) }
                    checkBtn.setOnClickListener { joinViewModel.checkCode(this, codeEt.text.toString()) }
                    cancelBtn.setOnClickListener {
                        codeEt.setText("")
                        popupWindow.dismiss()
                    }
                }
            }
        })
    }
}