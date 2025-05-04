package com.example.groovyshopping.ui

import androidx.lifecycle.MutableLiveData
import com.homecookapp.user.data.models.UserDataModel
import com.homecookapp.user.data.reporsitory.MainRepository
import com.homecookapp.user.utils.AppManger
import com.rushroomsapp.base.BaseViewModel

class AuthViewModel constructor(
    override var mainRepository: MainRepository,
    override val appManger: AppManger
) : BaseViewModel(mainRepository, appManger)

val loginResponse = MutableLiveData<UserDataModel?>()
val registrationResponse = MutableLiveData<UserDataModel?>()
val errorMessage = MutableLiveData<String>()

