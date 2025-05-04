package com.example.groovyshopping.user.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class CountriesResponse(
    var code: Int? = 0,
    var `data`: List<CountryDataModel>? = null,
    var errors: Any? = "",
    var message: String? = "",
    var status: Int? = 0
)
@Parcelize
data class CountryDataModel(
    @SerializedName("id") val id : Int,
    @SerializedName("name_ar") val name_ar : String,
    @SerializedName("name_en") val name_en : String,
    @SerializedName("lat") val lat : Double,
    @SerializedName("lng") val lng : Double,
    @SerializedName("image") val image : String,
    @SerializedName("phone_code") val phone_code : Int,
    @SerializedName("country_code") val country_code : String,
    @SerializedName("active") val active : Int,
    @SerializedName("order") val order : Int,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("name") val name : String,
    var selected : Boolean = false
) : Parcelable