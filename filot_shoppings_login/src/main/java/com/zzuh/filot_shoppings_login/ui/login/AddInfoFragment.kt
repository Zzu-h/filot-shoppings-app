package com.zzuh.filot_shoppings_login.ui.login


import android.os.Bundle
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
        binding.radioGrp.setOnCheckedChangeListener{group, id -> {
            when(id){
                R.id.radio_male -> Log.d("male", "tester")
                R.id.radio_female -> Log.d("female", "tester")
                else -> Log.d("else", "tester")
            }
        }}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root
}