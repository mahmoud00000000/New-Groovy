package com.example.groovyshopping.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.ActivityIntroductionBinding
import com.example.groovyshopping.base.BaseActivity
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.reflect.KClass

class IntroductionActivity : BaseActivity<ActivityIntroductionBinding, AuthViewModel>() {
    override fun resourceId(): Int = R.layout.activity_introduction

    val auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class



    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        window.statusBarColor = ContextCompat.getColor(this, R.color.g_blue)
    }

    override fun observer() {

        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = auth.currentUser
            if (currentUser != null && currentUser.isEmailVerified) {
                // هنا لازم نعمل محاولة نجيب التوكن من Firebase ونشوف هل فعلاً الأكاونت شغال ولا اتحذف
                currentUser.getIdToken(true)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // فعلاً الأكاونت شغال → روح للـ ShoppingActivity
                            startActivity(Intent(this, ShoppingActivity::class.java))
                            finish()
                        } else {
                            // الأكاونت اتحذف أو فيه مشكلة → روح للـ LoginRegister
                            goToLoginRegister()
                        }
                    }
            } else {
                // مفيش يوزر أصلاً أو الإيميل مش متأكد
                goToLoginRegister()
            }
        }, 500)

    }

    override fun clicks() {
        dataBinding.startBtn.setOnClickListener{
            startActivity(Intent(this, LoginRegisterActivity::class.java))
            finishAffinity()
        }
    }

    override fun callApis() {

    }

    private fun goToLoginRegister() {
        dataBinding.startBtn.setOnClickListener{
            startActivity(Intent(this, LoginRegisterActivity::class.java))
            finishAffinity()
        }
    }



}