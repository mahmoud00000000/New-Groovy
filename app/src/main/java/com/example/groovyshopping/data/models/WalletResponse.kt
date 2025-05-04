package com.homecookapp.user.data.models

import com.google.gson.annotations.SerializedName

data class WalletResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: WalletDataModel
)
data class WalletDataModel(
    @SerializedName("user_wallet") val user_wallet: String,
    @SerializedName("store_wallet") val store_wallet: String,
    @SerializedName("transactions") val transactions: WalletData
)
data class WalletData(
    @SerializedName("data") val data: List<TransactionDataModel>
)
data class TransactionDataModel(
    @SerializedName("id") val id : Int,
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("payment_method_id") val payment_method_id : Int,
    @SerializedName("store_id") val store_id : Int,
    @SerializedName("amount") val amount : String,
    @SerializedName("is_deposit") val is_deposit : Int,
    @SerializedName("status") val status : String,
    @SerializedName("payment_type") val payment_type : String,
    @SerializedName("note") val note : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("payment_method") val payment_method : PaymentMethodDataModel,
//    @SerializedName("bank_transfer") val bank_transfer : String
)
data class PaymentMethodDataModel (

    @SerializedName("id") val id : Int,
    @SerializedName("name_ar") val name_ar : String,
    @SerializedName("name_en") val name_en : String,
    @SerializedName("image") val image : String,
    @SerializedName("type") val type : String,
    @SerializedName("active") val active : Int,
    @SerializedName("list_type") val list_type : String,
    @SerializedName("name") val name : String,
    var selected:Boolean = false
)

data class PaymentMethodsResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<PaymentMethodDataModel>
)