package com.example.groovyshopping.ui.categories

import android.os.Bundle
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentBaseCategoryBinding
import com.example.groovyshopping.ui.AuthViewModel
import com.example.groovyshopping.user.base.BaseFragment
import kotlin.reflect.KClass

open class BaseCategoryFragment : BaseFragment<FragmentBaseCategoryBinding, AuthViewModel>(){
    override fun layoutResource(): Int = R.layout.fragment_base_category



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
