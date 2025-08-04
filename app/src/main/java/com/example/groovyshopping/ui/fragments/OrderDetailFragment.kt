package com.example.groovyshopping.ui.fragments

import android.os.Bundle
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentOrderDetailBinding
import com.example.groovyshopping.databinding.FragmentSearchBinding
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.viewmodels.OrderViewModel
import com.example.groovyshopping.user.base.BaseFragment
import kotlin.reflect.KClass

class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding, OrderViewModel>(){
    override fun layoutResource(): Int = R.layout.fragment_search



    override fun viewModelClass(): KClass<OrderViewModel> = OrderViewModel::class



    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
    }

    override fun observer() {

    }

    override fun clicks() {

    }

    override fun callApis() {

    }

}