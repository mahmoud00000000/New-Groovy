package com.example.groovyshopping.di.modules

import com.homecookapp.user.data.reporsitory.MainRepository
import com.homecookapp.user.utils.AppManger
import org.koin.dsl.module

val classesModule = module {
    single { MainRepository(get()) }
    single { AppManger(get()) }
}