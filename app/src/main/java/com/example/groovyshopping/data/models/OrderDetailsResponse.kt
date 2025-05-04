package com.example.groovyshopping.user.data.models

import com.example.groovyshopping.data.models.Payment_status_data
import com.example.groovyshopping.data.models.Status_data
import com.google.android.gms.wallet.WalletConstants
import com.google.gson.annotations.SerializedName



data class OrderDetailsResponse(

    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: OrderDetailsResponseData
)

data class OrderDetailsResponseData(

    @SerializedName("id") val id: Int,
    @SerializedName("code") val code: String,
    @SerializedName("status") val status: String,
    @SerializedName("payment_status") val payment_status: String,
    @SerializedName("receive_from_store") val receive_from_store: Int,
    @SerializedName("status_data") val status_data: Status_data,
    @SerializedName("payment_status_data") val payment_status_data: Payment_status_data,
    @SerializedName("payment_methods") val payment_methods: List<PaymentMethodDataModel>,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("store_id") val store_id: Int,
    @SerializedName("coupon_id") val coupon_id: Int,
    @SerializedName("payment_method_id") val payment_method_id: Int,
    @SerializedName("address_id") val address_id: Int,
    @SerializedName("delivery_cost") val delivery_cost: Double,
    @SerializedName("discount") val discount: Double,
    @SerializedName("total_prices") val total_prices: Double,
    @SerializedName("tax") val tax : Double,
    @SerializedName("grand_total") val grand_total: Double,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("user") val user: UserDataModel,
    @SerializedName("store") val store: Store,
    @SerializedName("payment_method") val payment_method: WalletConstants.PaymentMethod,
    @SerializedName("address") val address: Address,
    @SerializedName("rate") val rate: Double ?= null,
    @SerializedName("order_products") val order_products: List<Cart>
)