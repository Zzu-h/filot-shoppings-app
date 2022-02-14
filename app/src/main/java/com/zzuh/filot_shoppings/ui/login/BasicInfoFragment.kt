package com.zzuh.filot_shoppings.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzuh.filot_shoppings.data.repository.JoinRepository
import com.zzuh.filot_shoppings.databinding.FragmentBasicInfoBinding

class BasicInfoFragment(private val joinRepository: JoinRepository) : Fragment() {
    lateinit var binding: FragmentBasicInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBasicInfoBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root
}