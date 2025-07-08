package com.example.groovyshopping.ui.categories

import android.os.Bundle
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentTableBinding
import com.example.groovyshopping.ui.AuthViewModel
import com.example.groovyshopping.user.base.BaseFragment
import kotlin.reflect.KClass

class TableFragment : BaseCategoryFragment("Table"){
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
