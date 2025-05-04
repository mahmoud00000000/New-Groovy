package com.homecookapp.user.base

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import retrofit2.Response
import com.example.groovyshopping.databinding.DialogConfirmBinding
import com.example.groovyshopping.di.modules.BASE_URL
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.homecookapp.user.data.remote.DefaultDataModel
import com.homecookapp.user.data.remote.networkHandling.NetworkStatus
import com.example.groovyshopping.R
import com.homecookapp.user.utils.AppManger
import com.homecookapp.user.utils.myToast
import com.rushroomsapp.base.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

abstract class BaseBottomSheetDialog <T : ViewDataBinding, V : BaseViewModel> : BottomSheetDialogFragment(),
    NetworkStatus {


    val sharedPreferences: SharedPreferences by inject()

    lateinit var dataBinding: T

    val viewModel: V by lazy { getViewModel(null,viewModelClass()) }

    val appManger: AppManger by inject()

    var dialog: AlertDialog? = null

    override fun getTheme(): Int = R.style.BaseBottomSheetDialog

    abstract fun layoutResource(): Int

    abstract fun viewModelClass(): KClass<V>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dataBinding = DataBindingUtil.inflate(inflater, layoutResource(), container, false)
        viewModel.networkStatus = this
        dataBinding.lifecycleOwner = this

        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI(savedInstanceState)
        baseObserver()
        callApis()
        observer()
        clicks()
    }

    fun toggleLoadingDialog(show: Boolean) {

        if (dialog == null) {
            activity?.apply {
                dialog = AlertDialog.Builder(this)
                    .setView(R.layout.progress)
                    .setCancelable(false)
                    .create()

                dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

            }
        }

        if (!show) {
            dialog?.dismiss()
        } else if (show) {
            dialog?.show()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
    }

    private fun baseObserver(){

        viewModel.loading.observe(viewLifecycleOwner) {
            toggleLoadingDialog(it)
        }

        viewModel.showMassage.observe(viewLifecycleOwner) {
            myToast(it)
        }



    }
    abstract fun setUI(savedInstanceState: Bundle?)
    abstract fun observer()
    abstract fun clicks()
    abstract fun callApis()

    override fun onNoInternet() {

    }

    override fun onNotVerifyRequest(exception: String?) {

    }

    override fun onApiNotFound() {

    }

    override fun onNotAllowed() {

    }

    override fun onMakeAction(exception: String?) {
        try {
            Log.d("onMakeAction", exception.toString())
            val data = Gson().fromJson(exception, DefaultDataModel::class.java)
            myToast(data.message)
        }catch (e:Exception){
            Log.d("onMakeAction", e.message.toString())
        }

    }

    override fun onNotAuthorized(exception: String?) {
        var dialog: android.app.AlertDialog? = null
        val view = DialogConfirmBinding.inflate(LayoutInflater.from(activity))
        view.txtMassage.text = resources.getString(R.string.pleseLogin)
        view.btnOk.setOnClickListener {
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
//            openActivity(SplashActivity::class.java)
            appManger.logout()
        }
        view.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        dialog = android.app.AlertDialog.Builder(activity)
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
        Log.d("WTF onBadRequest", exception.toString())
        val data = Gson().fromJson(exception, DefaultDataModel::class.java)
        myToast(data.message)
    }

    override fun <T> onDynamicCode(response: Response<T>) {
        Log.d("onDynamicCode", response.toString())
        myToast(response.message().toString())
    }

    override fun onConnectionFail(exception: String?) {
        Log.e("onConnectionFail", exception.toString())
        val msg = if (exception.toString().contains(BASE_URL)) {
            exception.toString().replace(BASE_URL, "server")
        } else {
            exception.toString()
        }

        myToast(
            msg
        )
    }

    override fun onDuplicateItem(exception: String?) {

    }
}