package com.example.groovyshopping.ui.fragments

import android.os.Bundle
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentHomeBinding
import com.example.groovyshopping.ui.AuthViewModel
import com.example.groovyshopping.user.base.BaseFragment
import kotlin.reflect.KClass

class ProductDetailsFragment: BaseFragment<FragmentHomeBinding, AuthViewModel>() {
    override fun layoutResource(): Int = R.layout.fragment_product_details



    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class



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