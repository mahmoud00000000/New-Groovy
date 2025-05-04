package com.example.groovyshopping.user.data.remote


import com.example.groovyshopping.user.data.models.TransactionDataModel
import com.google.gson.annotations.SerializedName


data class DefaultDataModel(
    var code: Int,
    var status: Int,
    var data: DefaultData,
    var message: String
)

data class DefaultPayment(
    var id: String,
    var result: DefaultPaymentResult
)

data class DefaultPaymentResult(
    var code: String,
    var description: String
)

data class DefaultData(
    @SerializedName("user_id")
    var userId: Int,
    @SerializedName("stores_count")
    var stores_count: Int,
    @SerializedName("verification_code")
    var verificationCode: Int,
    @SerializedName("addresses_count")
    var addressesCount: Int,
    @SerializedName("count")
    var count: Int,
    var token: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("path")
    var path: String? = "",
    @SerializedName("amount")
    var amount: String? = "",
    @SerializedName("is_success")
    var is_success: Any,
    @SerializedName("send_push")
    var pushStatus: Int,
    @SerializedName("used_times")
    var usedTimes: Int? = 0,
    @SerializedName("is_verified")
    var is_verified: Boolean,
    @SerializedName("is_favourite")
    var is_favourite: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("registration_id")
    var registrationId: String,
    @SerializedName("body")
    var body: String,
    var favId: Int,
    var total_pending: Int,
    var total_approved: Int,
    var total_confirmed: Int,
    var reservation_id: Int,
    var payment_type: String,
    var transaction: TransactionDataModel,
    var versions: VersionDataModel
)

data class VersionDataModel(
    var force_update: ForceUpdateDataModel,
    var message: String
)

data class ForceUpdateDataModel(
    var android: AndroidDataModel
)

data class AndroidDataModel(
    @SerializedName("com.homecookapp.user")
    var user: UserAppDataModel
)

data class UserAppDataModel(
    var latest_version: String,
    var minimum_version: String,
    var update_url: String,
    var is_mandatory: Boolean
)

