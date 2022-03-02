package com.zzuh.filot_shoppings_login.ui.login

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
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
        binding.reSendBtn.setOnClickListener {
            var email = binding.emailEt.text.toString()
            if(joinViewModel.email == email) joinViewModel.sendCertificateCode()
            else{
                joinViewModel.doubleCheck.postValue(null)
                emailValidCheck(email)
            }
        }
        /*binding.certificateCodeBtn.setOnClickListener {
            var code = binding.certificateCodeEt.text.toString()
            if(code == "") Toast.makeText(activity, "코드를 입력해 주세요", Toast.LENGTH_SHORT).show()
            else joinViewModel.doCertificateCheck()
        }*/
    }
    private fun initializeViewModel(){
        joinViewModel.doubleCheck.observe(this, Observer {
            if(it == null) return@Observer
            else if(it) Toast.makeText(activity, "이 email은 이미 존재합니다.", Toast.LENGTH_SHORT).show()
            else {
                joinViewModel.sendCertificateCode()
                binding.emailBtn.visibility = View.GONE
                binding.reSendBtn.visibility = View.VISIBLE
            }
        })
        joinViewModel.certificateCheck.observe(this, Observer {
            if(it == null) return@Observer
            else if(!it) Toast.makeText(activity, "코드를 확인해 주세요", Toast.LENGTH_SHORT).show()
            else{
                binding.reSendBtn.isClickable = false
                binding.emailEt.isEnabled = false
                binding.emailEt.setBackgroundColor(Color.TRANSPARENT)
                //binding.certificateCodeLayout.visibility = View.GONE
            }
        })
    }
}