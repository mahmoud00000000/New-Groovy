package com.example.groovyshopping.screens.splash

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.example.groovyshopping.R
import com.example.groovyshopping.base.BaseActivity
import com.example.groovyshopping.databinding.ActivitySplashBinding
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.reflect.KClass



class SplashActivity : BaseActivity<ActivitySplashBinding, AuthViewModel>() {

    val PERMISSION_REQUEST_CODE: Int = 112
    override fun resourceId(): Int = R.layout.activity_splash

    val auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class

    override fun setUI(savedInstanceState: Bundle?) {
        FirebaseMessaging.getInstance().token.addOnSuccessListener { task ->
            if (task != null) {
                appManger.setPushToken(task)
                appManger.setNotificationCount(task)
                Log.d("pushToken", task)
            }
        }

       /* if (intent.extras != null) {
            when (intent.getStringExtra("type")) {
                "order" -> {
                    openActivity(
                        OrderDetailsActivity::class.java,
                        intent.putExtra(ORDER_ID, intent.getIntExtra("action_id", 0).toInt())
                    )
                }

                "product" -> {
                    openActivity(
                        ProductDetailsActivity::class.java, intent.putExtra(
                            PRODUCT_CAT_ID, intent.getIntExtra("action_id", 0).toInt()
                        )
                    )
                }
            }
        }*/


    }

    override fun clicks() {
        viewModel.getSetting()
    }

    override fun callApis() {
       // viewModel.getCountries()
    }

    override fun observer() {

        viewModel.settingResponse.observe(this) {
            if (Build.VERSION.SDK_INT > 32) {
                if (!shouldShowRequestPermissionRationale("112")) {
                    getNotificationPermission();
                }
            }
        }



    }



    override fun onNotAuthorized(exception: String?) {
        appManger.logout()
        Handler().postDelayed(Runnable {
            //openActivity(SelectCountryActivity::class.java)
            finishAffinity()
        }, 1200)
    }

    fun getNotificationPermission() {
        try {
            if (Build.VERSION.SDK_INT > 32) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            }
        } catch (e: Exception) {
            if (appManger.isLogin()) {
                if (appManger.getUserData()?.name.isNullOrEmpty()) {
                    appManger.logout()
                    //openActivity(SelectCountryActivity::class.java)
                    return
                }
                if (viewModel.settingResponse.value?.addressesCount!! > 0) {
                    //openActivity(AddressListActivity::class.java)
                } else {
                    //openActivity(
                        //AddAddressActivity::class.java,
                        //intent.putExtra("type", "addressList")
                    //)
                }
            } else {
                //openActivity(SelectCountryActivity::class.java)
            }
            finishAffinity()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // allow
                    if (appManger.isLogin()) {
                        if (viewModel.settingResponse.value?.addressesCount!! > 0) {
                            //openActivity(AddressListActivity::class.java)
                        } else {
                            //openActivity(
                                //AddAddressActivity::class.java,
                               // intent.putExtra("type", "addressList")
                            //)
                        }
                    } else {
                       // openActivity(SelectCountryActivity::class.java)
                    }
                    finishAffinity()
                } else {
                    //deny
                    if (appManger.isLogin()) {
                        if (viewModel.settingResponse.value?.addressesCount!! > 0) {
                            //openActivity(AddressListActivity::class.java)
                        } else {
                           // openActivity(
                               // AddAddressActivity::class.java,
                              //  intent.putExtra("type", "addressList")
                            //)
                        }
                    } else {
                       // openActivity(SelectCountryActivity::class.java)
                    }
                    finishAffinity()
                }
                return
            }
        }
    }
}