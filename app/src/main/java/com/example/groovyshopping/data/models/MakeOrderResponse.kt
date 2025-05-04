package com.example.groovyshopping.data.models

import com.google.gson.annotations.SerializedName


data class MakeOrderResponse(

    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: MakeOrderResponseData
)


data class MakeOrderResponseData(

    @SerializedName("receive_from_store") val receive_from_store: Int,
    @SerializedName("discount") val discount: Int,
    @SerializedName("total_prices") val total_prices: Double,
    @SerializedName("store_tax") val store_tax: Double,
    @SerializedName("system_ratio") val system_ratio: Double,
    @SerializedName("tax") val tax: Double,
    @SerializedName("delivery_cost") val delivery_cost: Int,
    @SerializedName("grand_total") val grand_total: Double,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("store_id") val store_id: Int,
    @SerializedName("code") val code: String,
    @SerializedName("status") val status: String,
    @SerializedName("coupon_id") val coupon_id: Int,
    @SerializedName("payment_method_id") val payment_method_id: Int,
    @SerializedName("address_id") val address_id: Int,
    @SerializedName("payment_status") val payment_status: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("id") val id: Int,
    @SerializedName("status_data") val status_data: Status_data,
    @SerializedName("payment_status_data") val payment_status_data: Payment_status_data
)

data class Payment_status_data(

    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("ar") val ar: String,
    @SerializedName("en") val en: String,
    @SerializedName("color") val color: String
)

data class Status_data(

    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("ar") val ar: String,
    @SerializedName("en") val en: String,
    @SerializedName("color") val color: String
)