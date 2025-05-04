package com.example.groovyshopping.user.application

import android.app.Application
import android.content.SharedPreferences
import com.example.groovyshopping.di.modules.classesModule
import com.example.groovyshopping.di.modules.networkModule
import com.example.groovyshopping.di.modules.preferencesModule
import com.example.groovyshopping.di.modules.viewModelModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class Application : Application() {

    companion object {
        var language: String = "ar"
        var appTheme: String = "light"
    }
    val sharedPreferences: SharedPreferences by inject()

    override fun onCreate() {
        super.onCreate()

        // AppsFlyer KEY TO Be Changed
//        AppsFlyerLib.getInstance().init("LeJYDPUMhxPnUuwtBP4UAJ", null, this)
//        AppsFlyerLib.getInstance().start(this)
//        AppsFlyerLib.getInstance().setDebugLog(true)

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@Application)
            modules(networkModule, preferencesModule, viewModelModule, classesModule)
        }
        language = if (sharedPreferences.getString("lang", "ar") == "en") {
            "en"
        } else {
            "ar"
        }
        appTheme = if (sharedPreferences.getString("theme", "light") == "light") {
            "light"
        } else {
            "dark"
        }
    }

}