package com.rushroomsapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abora.perfectobase.data.models.MoreDataModel
import com.abora.perfectobase.data.models.MorePagesData
import com.abora.perfectobase.data.models.PageDataModel
import com.google.gson.JsonObject
import com.homecookapp.user.data.models.PaymentMethodDataModel
import com.homecookapp.user.data.models.WalletDataModel
import com.homecookapp.user.data.remote.DefaultData
import com.homecookapp.user.data.remote.networkHandling.NetworkStatus
import com.homecookapp.user.data.reporsitory.MainRepository
import com.homecookapp.user.utils.AppManger
import kotlinx.coroutines.launch


open class BaseViewModel(
    open var mainRepository: MainRepository,
    open val appManger: AppManger
) : ViewModel() {
    lateinit var networkStatus: NetworkStatus
    val loading = MutableLiveData<Boolean>()
    val showMassage = MutableLiveData<String>()

    var viewPhoneLiveData = MutableLiveData<String>()
    var viewCodeLiveData = MutableLiveData<String>()
    var pagesResponse = MutableLiveData<MoreDataModel>()
    var pageResponse = MutableLiveData<PageDataModel?>()
    var transactionResponse = MutableLiveData<WalletDataModel?>()
    var chargeWalletResponse = MutableLiveData<DefaultData>()
    var paymentMethodsResponse = MutableLiveData<List<PaymentMethodDataModel>?>()
    val settingResponse = MutableLiveData<DefaultData>()

    val changePhoneResponse = MutableLiveData<DefaultData>()
    fun getSetting() {
//        loading.value = true
        viewModelScope.launch {
            mainRepository.getSetting(networkStatus).let {

                loading.value = false
                if (it.data?.data != null) {
                    val countries = it.data.data
                    countries?.let { it1 -> appManger.saveSetting(it1) }
                    settingResponse.value = it.data.data
                }

            }
        }
    }
    fun getPages() {
//        loading.value = true
        viewModelScope.launch {
            mainRepository.getPages(networkStatus).let {
                loading.value = false
                if (it.data != null) {
                    pagesResponse.postValue(it.data.pages)
                }

            }
        }
    }
    fun getPage(id: Int = 1) {
//        loading.value = true
        viewModelScope.launch {
            mainRepository.getPage(networkStatus, id).let {
                loading.value = false
                if (it.data != null) {
                    pageResponse.postValue(it.data)
                }

            }
        }
    }

    fun getTransaction() {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getTransaction(networkStatus).let {
                loading.value = false
                if (it.data != null) {
                    transactionResponse.postValue(it.data.data)
                }

            }
        }
    }

    fun chargeWallet(paymentId: Int, amount: String) {
        loading.value = true
        val data = JsonObject()
        data.addProperty("payment_method_id", paymentId)
        data.addProperty("amount", amount)
        data.addProperty("is_deposit", 1)
        viewModelScope.launch {
            mainRepository.chargeWallet(networkStatus, data).let {
                loading.value = false
                if (it.data != null) {
                    chargeWalletResponse.postValue(it.data.data)
                }

            }
        }
    }
    fun chargeCode(paymentId: Int, code: String) {

        loading.value = true
        val data = JsonObject()
        data.addProperty("payment_method_id", paymentId)
        data.addProperty("code", code)
        viewModelScope.launch {
            mainRepository.chargeCode(networkStatus, data).let {
                loading.value = false
                if (it.data != null) {
                    chargeWalletResponse.postValue(it.data.data)
                }

            }
        }
    }

    fun getPaymentMethods(paymentType: String) {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getPaymentMethods(networkStatus, paymentType).let {
                loading.value = false
                if (it.data != null) {
                    paymentMethodsResponse.postValue(it.data.data)
                }

            }
        }
    }

}