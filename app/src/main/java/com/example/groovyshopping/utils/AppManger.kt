package com.homecookapp.user.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.homecookapp.user.data.models.AddressResponse
import com.homecookapp.user.data.models.CountryDataModel
import com.homecookapp.user.data.models.UserDataModel


class AppManger constructor(var sharedPreferences: SharedPreferences) {

    companion object {
        const val USER = "user"
        const val SETTING = "setting"
        const val PHONE = "phone"
        const val EMAIL = "email"
        const val TOKEN = "token"
        const val COUNTRIES = "countries"
        const val USER_COUNTRIES = "user_countries"
        const val USER_ADDRESS = "user_address"
        const val ID = "id"
        const val NAME = "name"
        const val TYPE = "type"
        const val VENDOR = "vendor"
        const val PROVIDER = "provider"
        const val IS_FIRST_TIME = "isFirstTime"
        const val IS_FIRST_Update = "isFirstUpdate"
        const val LATITUDE = "lat"
        const val LONGITUDE = "lng"
        const val ADDRESS_NAME = "address_name"
        const val ADDRESS_DES = "address_descrioption"
        const val CART_DATA = "cartData1"

    }

    fun isLogin() = sharedPreferences.contains(TOKEN)

    fun isVendorLogin() = sharedPreferences.getString(TYPE, "").equals(VENDOR)

    fun logout() {
        sharedPreferences.edit().remove(USER).apply()
        sharedPreferences.edit().remove(PHONE).apply()
        sharedPreferences.edit().remove(EMAIL).apply()
        sharedPreferences.edit().remove(TOKEN).apply()
        sharedPreferences.edit().remove(ID).apply()
        sharedPreferences.edit().remove(NAME).apply()
        sharedPreferences.edit().remove(TYPE).apply()
        sharedPreferences.edit().remove(PROVIDER).apply()
    }

    fun saveUserData(loginData: UserDataModel?) {
        if (loginData?.token.isNullOrEmpty().not())
            sharedPreferences.edit().putString(TOKEN, loginData?.token).apply()
        sharedPreferences.edit().putString(ID, loginData?.id.toString()).apply()
        sharedPreferences.edit().putString(NAME, loginData?.name).apply()
        sharedPreferences.edit().putString(PHONE, loginData?.phone).apply()
        sharedPreferences.edit().putString(EMAIL, loginData?.email).apply()
        sharedPreferences.edit().putString(USER, Gson().toJson(loginData)).apply()
    }

    fun getUserData(): UserDataModel? {
        val user = sharedPreferences.getString(USER, "")
        return user?.let {
            Gson().fromJson(user, UserDataModel::class.java)
        }
    }

    fun saveSetting(loginData: com.homecookapp.user.data.remote.DefaultData) {
        sharedPreferences.edit().putString(SETTING, Gson().toJson(loginData)).apply()
    }

    fun getSetting(): com.homecookapp.user.data.remote.DefaultData? {
        val user = sharedPreferences.getString(SETTING, "")
        return user?.let {
            Gson().fromJson(user, com.homecookapp.user.data.remote.DefaultData::class.java)
        }
    }

    fun saveCountries(countries: List<CountryDataModel>) {
        sharedPreferences.edit().putString(COUNTRIES, Gson().toJson(countries)).apply()
    }

    fun saveUserCountry(country: CountryDataModel) {
        sharedPreferences.edit().putString(USER_COUNTRIES, Gson().toJson(country)).apply()
    }

    fun setPushToken(task: String) {
        sharedPreferences.edit().putString("pushToken", task).apply()
    }

    fun getPushToken(): String {
        return sharedPreferences.getString("pushToken", "").toString()
    }


    fun getUserCountry(): CountryDataModel? {
        val country = sharedPreferences.getString(USER_COUNTRIES, "")
        return country?.let {
            Gson().fromJson(country, CountryDataModel::class.java)
        }
    }


    fun saveUserAddress(country: AddressResponse.AddressDataModel) {
        sharedPreferences.edit().putString(USER_ADDRESS, Gson().toJson(country)).apply()
    }

    fun getUserAddress(): AddressResponse.AddressDataModel? {
        val address = sharedPreferences.getString(USER_ADDRESS, "")
        return address?.let {
            Gson().fromJson(address, AddressResponse.AddressDataModel::class.java)
        }
    }

    fun getCountries(): List<CountryDataModel>? {
        val countriesString = sharedPreferences.getString(COUNTRIES, "")
        val countries =
            Gson().fromJson(countriesString, Array<CountryDataModel>::class.java).toList()
        if (getUserCountry() != null)
            countries.forEach {
                if (it.id == getUserCountry()?.id) {
                    it.selected = true
                }
            }
        else {
            val country = countries[0]
            country.selected = true
            saveUserCountry(country)
        }
        return countries?.let {
            Gson().fromJson(Gson().toJson(it), Array<CountryDataModel>::class.java).toList()
        }
    }

    fun setUserLocation(lat: String, lng: String,name:String,description:String) {
        sharedPreferences.edit().putString(LATITUDE, lat).apply()
        sharedPreferences.edit().putString(LONGITUDE, lng).apply()
        sharedPreferences.edit().putString(ADDRESS_NAME, name).apply()
        sharedPreferences.edit().putString(ADDRESS_DES, description).apply()
    }

    fun getUserLat() = sharedPreferences.getString(LATITUDE, "0.0").toString()

    fun getUserLng() = sharedPreferences.getString(LONGITUDE, "0.0").toString()

    fun getUserAddressName() = sharedPreferences.getString(ADDRESS_NAME, "0.0").toString()
    fun getUserAddressDescription() = sharedPreferences.getString(ADDRESS_DES, "0.0").toString()

    fun isFirstTime(): Boolean {
        val isFirst = sharedPreferences.getBoolean(IS_FIRST_TIME, true)
        if (isFirst) {
            sharedPreferences.edit().putBoolean(IS_FIRST_TIME, false).apply()
        }
        return isFirst
    }

    fun isFirstUpdate(): Boolean {
        val isFirst = sharedPreferences.getBoolean(IS_FIRST_Update, true)
        if (isFirst) {
            sharedPreferences.edit().putBoolean(IS_FIRST_Update, false).apply()
        }
        return isFirst
    }
    fun removeFirstUpdate(){
        sharedPreferences.edit().putBoolean(IS_FIRST_Update, false).apply()
    }

    fun setCountry(countryCode: String) {
        sharedPreferences.edit().putString("country", countryCode).apply()
    }

    fun getCountry(): String {
        return sharedPreferences.getString("country", "").toString()
    }

    fun setNotificationCount(count: String) {
        sharedPreferences.edit().putString("notification_count", count).apply()
    }

    fun getNotificationCount(): Int {
        return sharedPreferences.getString("notification_count", "0").toString().toInt()
    }

    fun setPhoneCode(countryCode: String) {
        sharedPreferences.edit().putString("phoneCode", countryCode).apply()
    }

    fun getPhoneCode(): String {
        return sharedPreferences.getString("phoneCode", "EG").toString()
    }

    fun pushToken(token: String? = null): String? {
        return if (token.isNullOrEmpty()) {
            sharedPreferences.getString("pushToken", "Not Allowed").toString()
        } else {
            sharedPreferences.edit().putString("pushToken", token).apply()
            null
        }

    }

   /* fun saveOrderData(cardData: CartDataRequest) {
        val savedOrder = getSavedOrderData()
        cardData.totalPrice = cardData.totalPrice?.plus(savedOrder?.totalPrice ?: 0.0)
        sharedPreferences.edit().putString(CART_DATA, Gson().toJson(cardData)).apply()
    }

    fun getSavedOrderData(): CartDataRequest? {
        val cartData = sharedPreferences.getString(CART_DATA, "")
        return cartData?.let {
            Gson().fromJson(cartData, CartDataRequest::class.java)
        }
    }

    fun clearOrderData(){
        sharedPreferences.edit().remove(CART_DATA).apply()
    }*/

}