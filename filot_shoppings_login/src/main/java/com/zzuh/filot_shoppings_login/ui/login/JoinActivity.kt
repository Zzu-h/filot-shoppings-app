package com.zzuh.filot_shoppings.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.zzuh.filot_shoppings.R
import com.zzuh.filot_shoppings.data.repository.JoinRepository
import com.zzuh.filot_shoppings.databinding.ActivityJoinBinding
import com.zzuh.filot_shoppings.ui.login.viewmodel.JoinViewModel

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
            }
        }
        binding.cancelBtn.setOnClickListener {
            // repository delete
            finish()
        }
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
}