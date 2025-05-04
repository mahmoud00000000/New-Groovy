package com.example.groovyshopping.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.example.groovyshopping.data.remote.RetrofitApi
import com.example.groovyshopping.user.application.Application
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
//import com.homecookapp.user.screens.hyperPayment.HyperPayCredential.getPaymentTokenCredential
import com.homecookapp.user.utils.AppManger
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory.Companion.invoke
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit


//const val BASE_URL = "https://02bc-197-58-228-33.ngrok-free.app/api/"
const val BASE_URL = "https://manage.homecookapp.info/api/"

val networkModule = module {
    single { getLoggingInterceptor() }
    single { getOkHttp(get(), get(), get(),get()) }
    single { getRetrofit(get()) }
    single { getRetrofitApi(get()) }
}

fun getLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    return httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
}

fun getOkHttp(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    context: Context,
    sharedPreferences:SharedPreferences,
    appManger: AppManger
): OkHttpClient {
    return OkHttpClient().newBuilder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor { chain ->

            val request = chain.request()
            val builder = request.newBuilder()
                .addHeader("Accept-Language", Application.language)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Device-Type", "android")
                .addHeader("user-type", "client app")
                .addHeader("Device-Name", Build.MODEL)
                .addHeader("Device-OS-Version", Build.VERSION.RELEASE)
                .addHeader(
                    "Device-UDID",
                    Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
                )
                .addHeader(
                    "Device-Push-Token",
                    appManger.getPushToken()
                )
                .addHeader("mobile_version", Build.VERSION.SDK_INT.toString())

            /*try {
                val pInfo: PackageInfo =
                    context.packageManager.getPackageInfo(context.packageName, 0)
                val version = pInfo.versionName
                builder.addHeader("App-Version", version)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                builder.addHeader("App-Version", "1")
            }*/

            if (sharedPreferences.contains("token")) {
                if (request.url.toString().contains("oppwa")) {

                    /*builder.addHeader(
                        "Authorization",
                        getPaymentTokenCredential()
                    )*/
                    Log.d("token", "token: ${sharedPreferences.getString("token", "")}")
                } else {

                    builder.addHeader(
                        "Authorization",
                        "Bearer ${sharedPreferences.getString("token", "")}"
                    )
                    Log.d("token", "token: ${sharedPreferences.getString("token", "")}")
                }
            }
            if (sharedPreferences.contains(AppManger.LATITUDE)) {
                sharedPreferences.getString(AppManger.LATITUDE, "0.0")
                    ?.let { builder.addHeader("lat", it) }
                sharedPreferences.getString(AppManger.LONGITUDE, "0.0")
                    ?.let { builder.addHeader("lng", it) }
            }
            val response = chain.proceed(builder.build())
            response
        }.build()
}


fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val gson = GsonBuilder()
        .registerTypeAdapter(HttpUrl::class.java, UrlDeserializer())
        .create()
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

fun getRetrofitApi(retrofit: Retrofit): RetrofitApi {
    return retrofit.create(RetrofitApi::class.java)
}

class UrlDeserializer : JsonDeserializer<HttpUrl> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): HttpUrl =
        json.asString.toHttpUrl()

}