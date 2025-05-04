package com.example.groovyshopping.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.groovyshopping.R
import com.example.groovyshopping.base.BaseActivity
import com.example.groovyshopping.databinding.ActivityLoginRegisterBinding
import kotlin.reflect.KClass

class LoginRegisterActivity : BaseActivity<ActivityLoginRegisterBinding, AuthViewModel>() {
    override fun resourceId(): Int = R.layout.activity_login_register



    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class



    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
    }

    override fun observer() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.g_blue)
    }

    override fun clicks() {

        dataBinding.loginBtn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))

        }

        dataBinding.registerBtn.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))

        }

    }

    override fun callApis() {

    }

}