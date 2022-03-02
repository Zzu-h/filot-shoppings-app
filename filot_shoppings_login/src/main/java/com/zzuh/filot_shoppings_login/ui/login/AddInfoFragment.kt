package com.zzuh.filot_shoppings_login.ui.login


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzuh.filot_shoppings_login.R
import com.zzuh.filot_shoppings_login.databinding.FragmentAddInfoBinding
import com.zzuh.filot_shoppings_login.ui.login.viewmodel.JoinViewModel

class AddInfoFragment(private val joinViewModel: JoinViewModel) : Fragment() {
    lateinit var binding: FragmentAddInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAddInfoBinding.inflate(layoutInflater)
        binding.radioGrp.setOnCheckedChangeListener{group, id ->
            when(id){
                R.id.radio_male -> joinViewModel.isMale = true
                R.id.radio_female -> joinViewModel.isMale = false
                else -> joinViewModel.isMale = null
            }
        }
        initializeEditText()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        joinViewModel.roadAddress?.let { binding.roadAddressEt.setText(joinViewModel.roadAddress)}
        joinViewModel.detailAddress?.let{ binding.detailAddressEt.setText(joinViewModel.detailAddress)}
        joinViewModel.name?.let{ binding.nameEt.setText(joinViewModel.name)}
        joinViewModel.phoneNumber?.let{ binding.phoneNumberEt.setText(joinViewModel.phoneNumber)}
        return binding.root
    }

    private fun initializeEditText(){
        binding.roadAddressEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(email: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(roadAddress: Editable?) {
                joinViewModel.roadAddress = roadAddress?.toString()
            }
        })
        binding.detailAddressEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(detailAddress: Editable?) {
                joinViewModel.detailAddress = detailAddress?.toString()
            }
        })
        binding.nameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(name: Editable?) {
                joinViewModel.name = name?.toString()
            }
        })
        binding.phoneNumberEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(phoneNumber: Editable?) {
                joinViewModel.phoneNumber = phoneNumber?.toString()
            }
        })
    }
}