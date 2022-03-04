package com.zzuh.filot_shoppings.ui.main

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zzuh.filot_shoppings.data.repository.NetworkState
import com.zzuh.filot_shoppings.data.vo.BasketItem
import com.zzuh.filot_shoppings.databinding.FragmentCartBinding
import com.zzuh.filot_shoppings.ui.main.adapter.CartListAdapter
import com.zzuh.filot_shoppings.ui.main.viewmodel.CartViewModel
import com.zzuh.filot_shoppings.ui.main.viewmodel.CartViewModelFactory

class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    lateinit var cartViewModel: CartViewModel
    lateinit var cartListAdapter: CartListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCartBinding.inflate(layoutInflater)
    }
    private fun loginCheckAndInitialize(){
        val spToken = this.activity?.getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        val token = spToken?.getString("token", null)

        if(token == null){
            binding.needLoginTv.visibility = View.VISIBLE
            binding.loginOkLayout.visibility = View.GONE
            return
        }

        binding.needLoginTv.visibility = View.GONE
        binding.loginOkLayout.visibility = View.VISIBLE

        cartViewModel = ViewModelProvider(this, CartViewModelFactory(token))
            .get(CartViewModel::class.java)
        cartListAdapter = CartListAdapter(requireContext(), cartViewModel, emptyList())
        binding.cartListRecyclerView.adapter = cartListAdapter

        initViewModel()
    }
    private fun initViewModel(){
        cartViewModel.networkState.observe(this.viewLifecycleOwner, Observer {
            when(it){
                NetworkState.LOADING -> {
                    binding.loadingBar.visibility = View.VISIBLE
                    binding.cartListRecyclerView.visibility = View.GONE
                    binding.cartTabLayout.visibility = View.GONE
                    binding.noItemTv.visibility = View.GONE
                    binding.totPriceLayout.visibility = View.GONE
                    binding.txtError.visibility = View.GONE
                }
                NetworkState.ERROR -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.txtError.visibility = View.VISIBLE
                }
                NetworkState.LOADED -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.txtError.visibility = View.GONE
                    binding.cartTabLayout.visibility = View.VISIBLE
                    binding.totPriceLayout.visibility = View.VISIBLE
                }
            }
        })
        cartViewModel.basket.observe(this.viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                binding.noItemTv.visibility = View.VISIBLE
                binding.cartListRecyclerView.visibility = View.GONE
            }
            else{
                binding.noItemTv.visibility = View.GONE
                binding.cartListRecyclerView.visibility = View.VISIBLE
                calTotPrice(it)
                cartListAdapter.updateData(it)
            }
        })
        cartViewModel.totPrice.observe(this.viewLifecycleOwner, Observer {
            binding.priceTv.text = "KRW $it₩"
        })
    }
    private fun calTotPrice(list: List<BasketItem>){
        var price = 0
        for(item in list)
            price += (item.selectedCount * item.productPrice)

        cartViewModel.totPrice.postValue(price)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginCheckAndInitialize()
        return binding.root
    }
}