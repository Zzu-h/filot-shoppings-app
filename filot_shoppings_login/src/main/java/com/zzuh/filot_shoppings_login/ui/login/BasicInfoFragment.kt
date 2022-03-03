package com.zzuh.filot_shoppings_login.ui.login

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.zzuh.filot_shoppings_login.R
import com.zzuh.filot_shoppings_login.data.repository.NetworkState
import com.zzuh.filot_shoppings_login.databinding.FragmentBasicInfoBinding
import com.zzuh.filot_shoppings_login.ui.login.viewmodel.JoinViewModel

class BasicInfoFragment(private val joinViewModel: JoinViewModel) : Fragment() {
    lateinit var binding: FragmentBasicInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBasicInfoBinding.inflate(layoutInflater)

        initializeBtn()
        initializeViewModel()
        initializeEditText()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        joinViewModel.email?.let { binding.emailEt.setText(joinViewModel.email)}
        joinViewModel.password?.let{ binding.passwordEt.setText(joinViewModel.password)}
        joinViewModel.checkPassword?.let{ binding.checkPasswordEt.setText(joinViewModel.checkPassword)}
      return binding.root
    }

    private fun initializeEditText(){
        binding.emailEt.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(email: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(email: Editable?) {
                joinViewModel.email = email?.toString()
            }
        })
        binding.passwordEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(password: Editable?) {
                joinViewModel.password = password?.toString()
            }
        })
        binding.checkPasswordEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(password: Editable?) {
                joinViewModel.passwordCheck = (joinViewModel.password == password?.toString())
                joinViewModel.checkPassword = password?.toString()
                if(!joinViewModel.passwordCheck)
                    binding.checkPasswordEt.error = "비밀번호가 다릅니다!"
            }
        })
    }

    private fun emailValidCheck(email: String){
        var pattern = android.util.Patterns.EMAIL_ADDRESS
        if(email == "") Toast.makeText(activity, "email을 입력해 주세요", Toast.LENGTH_SHORT).show()
        else if(!pattern.matcher(email).matches()) Toast.makeText(activity, "email 형식이 아닙니다!", Toast.LENGTH_SHORT).show()
        else joinViewModel.doDoubleCheck()
    }
    private fun initializeBtn(){
        binding.emailBtn.setOnClickListener {
            var email = binding.emailEt.text.toString()
            joinViewModel.email = email
            emailValidCheck(email)
        }

    }
    private fun initializeViewModel(){
        joinViewModel.doubleCheck.observe(this, Observer {
            when(it){
                NetworkState.LOGINFAIL -> Toast.makeText(activity, "이 email은 이미 존재합니다.", Toast.LENGTH_SHORT).show()
                NetworkState.ERROR -> Toast.makeText(activity, "네트워크 상태를 확인해주세요", Toast.LENGTH_SHORT).show()
                NetworkState.LOADED -> {
                    Toast.makeText(activity, "이 email을 사용합니다.", Toast.LENGTH_SHORT).show()
                    binding.emailBtn.isClickable = false
                    binding.emailBtn.background = resources.getDrawable(R.color.background_gray, null)
                    binding.emailEt.isFocusable = false
                    binding.emailBtn.isFocusableInTouchMode = true
                    binding.emailEt.inputType = InputType.TYPE_NULL
                }
                else -> return@Observer
            }
        })
    }
}