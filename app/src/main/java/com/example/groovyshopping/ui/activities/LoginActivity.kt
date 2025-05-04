package com.example.groovyshopping.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.groovyshopping.R
import com.example.groovyshopping.base.BaseActivity
import com.example.groovyshopping.databinding.ActivityLoginBinding
import com.example.groovyshopping.ui.AuthViewModel
import kotlin.reflect.KClass

class LoginActivity : BaseActivity<ActivityLoginBinding, AuthViewModel>(){
    override fun resourceId(): Int = R.layout.activity_login


    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class



    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        window.statusBarColor = ContextCompat.getColor(this, R.color.g_blue)
    }

    override fun observer() {


        viewModel.loginResponse.observe(this) {
            startActivity(Intent(this, ShoppingActivity::class.java))
            finish()
        }

        viewModel.errorMessage.observe(this){
            Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
        }

    }

    override fun clicks() {

        dataBinding.tvDntHaveAccount.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        dataBinding.btnLogin.setOnClickListener{

            viewModel.loginUser()

        }


    }

    override fun callApis() {

    }



}