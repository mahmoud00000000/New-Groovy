package com.example.groovyshopping.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.ActivityIntroductionBinding
import com.example.groovyshopping.base.BaseActivity
import kotlin.reflect.KClass

class IntroductionActivity : BaseActivity<ActivityIntroductionBinding, AuthViewModel>() {
    override fun resourceId(): Int = R.layout.activity_introduction



    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class



    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
    }

    override fun observer() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.g_blue)
    }

    override fun clicks() {
        dataBinding.startBtn.setOnClickListener{
            startActivity(Intent(this, LoginRegisterActivity::class.java))
            finishAffinity()
        }
    }

    override fun callApis() {

    }

}