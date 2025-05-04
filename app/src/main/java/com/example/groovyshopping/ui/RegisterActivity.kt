package com.example.groovyshopping.ui

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.groovyshopping.base.BaseActivity
import kotlin.reflect.KClass
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.ActivityRegisterBinding


class RegisterActivity : BaseActivity<ActivityRegisterBinding, AuthViewModel>() {
    override fun resourceId(): Int = R.layout.activity_register



    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class


    override fun setUI(savedInstanceState: Bundle?) {

    }

    override fun observer() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.g_blue)

    }

    override fun clicks() {

    }

    override fun callApis() {

    }

}