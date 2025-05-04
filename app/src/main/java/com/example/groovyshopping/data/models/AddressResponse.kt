package com.homecookapp.user.data.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("data")
    var `data`: List<AddressDataModel>,
    @SerializedName("message")
    var message: String,
    @SerializedName("status")
    var status: Int
): Parcelable{
    @Parcelize
    data class AddressDataModel(
            @SerializedName("id")
            var id: Int?,
            @SerializedName("user_id")
            var user_id: String?,
            @SerializedName("type")
            var type: String?,
            @SerializedName("type_name")
            var typeName: String?,
            @SerializedName("name")
            var name: String,
            @SerializedName("title")
            var title: String,
            @SerializedName("description")
            var description: String?,
            @SerializedName("address")
            var address: String?,
            @SerializedName("lat")
            var lat: String?,
            @SerializedName("lng")
            var lng: String?,
            @SerializedName("deleted_at")
            var deleted_at: String?,
            @SerializedName("created_at")
            var created_at: String?,
            @SerializedName("updated_at")
            var updated_at: String?,
            @SerializedName("building_number")
            var building_number: String?,
            @SerializedName("floor")
            var floor: String?,
            @SerializedName("apartment_number")
            var apartment_number: String?,
            @SerializedName("country")
            var country: CountryDataModel,
            var selected: Boolean=false
    ) : Parcelable
}

