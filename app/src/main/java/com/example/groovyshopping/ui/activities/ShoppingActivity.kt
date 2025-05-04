package com.example.groovyshopping.ui.activities

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.groovyshopping.R
import com.example.groovyshopping.base.BaseActivity
import com.example.groovyshopping.databinding.ActivityShoppingBinding
import com.example.groovyshopping.ui.AuthViewModel
import kotlin.reflect.KClass

class ShoppingActivity : BaseActivity<ActivityShoppingBinding, AuthViewModel>() {
    override fun resourceId(): Int = R.layout.activity_shopping



    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class



    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel

        window.statusBarColor = ContextCompat.getColor(this, R.color.g_blue)

        val navController = findNavController(R.id.shoppingHostFragment)
        dataBinding.bottomNavigation.setupWithNavController(navController)

    }

    override fun observer() {

    }

    override fun clicks() {

    }

    override fun callApis() {

    }

}