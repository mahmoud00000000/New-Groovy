package com.example.groovyshopping.base

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.homecookapp.user.utils.AppManger
import com.homecookapp.user.utils.MyUtils.myToast
import com.example.groovyshopping.base.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.DialogConfirmBinding
import com.example.groovyshopping.di.modules.BASE_URL
import com.example.groovyshopping.ui.activities.LoginActivity
import com.google.gson.Gson
import com.example.groovyshopping.user.application.Application
import com.example.groovyshopping.screens.splash.SplashActivity
import com.example.groovyshopping.user.data.remote.DefaultDataModel
import com.example.groovyshopping.user.data.remote.networkHandling.NetworkStatus
import com.homecookapp.user.utils.openActivity
import retrofit2.Response
import java.util.Locale


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(),
    NetworkStatus {

    lateinit var dataBinding: T

    val appManger: AppManger by inject()

    val viewModel: V by lazy { getViewModel(null,viewModelClass()) }

    val sharedPreferences: SharedPreferences by inject()

    var dialog: AlertDialog? = null



    abstract fun resourceId(): Int

    abstract fun viewModelClass(): KClass<V>


    override fun onCreate(savedInstanceState: Bundle?) {
        setAppMode()
        checkLang()
        super.onCreate(savedInstanceState)
        init()

        baseObserver()

        setUI(savedInstanceState)
        clicks()
        callApis()
        observer()

    }

    private fun init() {

        viewModel.networkStatus = this
        dataBinding = DataBindingUtil.setContentView(this, resourceId())
        dataBinding.lifecycleOwner = this


    }

    private fun baseObserver() {

        viewModel.loading.observe(this, Observer {
            toggleLoadingDialog(it)
        })

        viewModel.showMassage.observe(this, Observer {
            myToast(it)
        })

    }


    abstract fun setUI(savedInstanceState: Bundle?)
    abstract fun observer()
    abstract fun clicks()
    abstract fun callApis()

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_change, R.anim.slide_down)

    }

    fun setAppMode() {
        if (sharedPreferences.getString("theme", "light") == "light") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Application.appTheme = "light"
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Application.appTheme = "dark"
        }
        sharedPreferences.edit().putString("theme", Application.appTheme).apply()
    }
    fun changeAppTheme() {
        if (sharedPreferences.getString("theme", "light") == "light") {
            Application.appTheme = "dark"
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Application.appTheme = "light"
        }
        sharedPreferences.edit().putString("theme", Application.appTheme).apply()
    }
    fun changeAppLang() {
        if (Application.language == "en") {
            Application.language = "ar"
        } else if (Application.language == "ar") {
            Application.language = "en"
        }
        sharedPreferences.edit().putString("lang", Application.language).apply()
        checkLang()
        openActivity(SplashActivity::class.java)
        finishAffinity()
    }

    fun checkLang() {
        val config = resources.configuration
        val lang = Application.language // your language code
        val locale = Locale(lang)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(locale)
        else
            config.locale = locale

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createConfigurationContext(config)
            applicationContext.createConfigurationContext(config)
        }
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun changeLocale(context: Context, locale: String?) {
        val res: Resources = context.resources
        val conf: Configuration = res.configuration
        conf.locale = Locale(locale)
        res.updateConfiguration(conf, res.displayMetrics)
    }

    override fun onNoInternet() {

    }

    override fun onNotVerifyRequest(exception: String?) {

    }

    override fun onApiNotFound() {

    }

    override fun onNotAllowed() {

    }

    override fun onNotAuthorized(exception: String?) {
        var dialog: android.app.AlertDialog? = null
        val view = DialogConfirmBinding.inflate(LayoutInflater.from(this))
        view.txtMassage.text = resources.getString(R.string.pleseLogin)
        view.btnOk.setOnClickListener {
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
            openActivity(LoginActivity::class.java)
        }
        view.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        dialog = android.app.AlertDialog.Builder(this)
            .setView(view.root)
            .setCancelable(false)
            .show()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    override fun onServerSideError(exception: String?) {
        var data = Gson().fromJson(exception, DefaultDataModel::class.java)
        myToast(data.message)
    }

    override fun onBadRequest(exception: String?) {
        var data = Gson().fromJson(exception, DefaultDataModel::class.java)
        myToast(data.message)
    }

    override fun onMakeAction(exception: String?) {
        try {
            Log.d("onMakeAction", exception.toString())
            val data = Gson().fromJson(exception, DefaultDataModel::class.java)
            myToast(data.message)
        } catch (e: Exception) {
            Log.d("onMakeAction", e.message.toString())
        }
    }

    override fun <T> onDynamicCode(response: Response<T>) {
        Log.d("onDynamicCode", response.toString())
        try {
            val data = Gson().fromJson(
                response.errorBody()?.source()?.buffer?.readUtf8(),
                DefaultDataModel::class.java
            )
            myToast(data.message)
        } catch (e: Exception) {

        }
    }

    override fun onConnectionFail(exception: String?) {
        Log.e("onConnectionFail", exception.toString())
        val msg = if (exception.toString()
                .contains(BASE_URL.replace("https://", "").replace("/api/", ""))
        ) {
            exception.toString()
                .replace(BASE_URL.replace("https://", "").replace("/api/", ""), "server")
        } else {
            exception.toString()
        }
        if (msg != "Job was cancelled")
            myToast(
                msg
            )
    }

    fun toggleLoadingDialog(show: Boolean) {

        if (dialog == null) {
            dialog = AlertDialog.Builder(this)
                .setView(R.layout.progress)
                .setCancelable(false)
                .create()

            dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        }

        if (!show)
            dialog?.dismiss()
        else if (show)
            dialog?.show()


    }

    override fun onPause() {
        super.onPause()
        dialog?.dismiss()
    }

    override fun onStop() {
        super.onStop()
        dialog?.dismiss()
    }


    override fun onDuplicateItem(exception: String?) {

    }

}