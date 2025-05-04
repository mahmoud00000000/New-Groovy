package com.homecookapp.user.data.models

import com.google.gson.annotations.SerializedName

data class OrdersListResponse(

    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: OrdersListResponseData
)
data class CancellationResponse(

    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<CancellationDataModel>
)

data class OrdersListResponseData(

    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("per_page") val per_page: Int,
    @SerializedName("current_page") val current_page: Int,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("last_page") val last_page: Int,
    @SerializedName("data") val ordersData: List<OrdersData>
)

data class OrdersData(

    @SerializedName("id") val id: Int,
    @SerializedName("code") val code: String,
    @SerializedName("status") val status: String,
    @SerializedName("payment_status") val payment_status: String,
    @SerializedName("receive_from_store") val receive_from_store: Int,
    @SerializedName("status_data") val status_data: Status_data,
    @SerializedName("payment_status_data") val payment_status_data: Payment_status_data,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("store_id") val store_id: Int,
    @SerializedName("coupon_id") val coupon_id: Int,
    @SerializedName("payment_method_id") val payment_method_id: Int,
    @SerializedName("address_id") val address_id: Int,
    @SerializedName("delivery_cost") val delivery_cost: Double,
    @SerializedName("discount") val discount: Double,
    @SerializedName("total_prices") val total_prices: Double,
    @SerializedName("grand_total") val grand_total: Double,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("user") val user: UserDataModel,
    @SerializedName("store") val store: Store,
    @SerializedName("payment_method") val payment_method: PaymentMethod,
    @SerializedName("address") val address: Address,
    @SerializedName("order_products") val order_products: List<Order_products>
)

data class CancellationDataModel(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("active") val active: String,
)

data class Address(

    @SerializedName("id") val id: Int,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("title") val title: String,
    @SerializedName("address") val address: String,
    @SerializedName("description") val description: String,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("country_id") val country_id: Int,
    @SerializedName("user_distance") val user_distance: Double
)


data class Order_products(

    @SerializedName("id") val id: Int,
    @SerializedName("order_id") val order_id: Int,
    @SerializedName("product_id") val product_id: Int,
    @SerializedName("price") val price: Double,
    @SerializedName("qty") val qty: Int,
    @SerializedName("note") val note: String,
    @SerializedName("total_item_price") val total_item_price: Double,
    @SerializedName("product") val product: Product,
    @SerializedName("options") val options: List<Options>
)

data class PaymentMethod(

    @SerializedName("id") val id: Int,
    @SerializedName("name_ar") val name_ar: String,
    @SerializedName("name_en") val name_en: String,
    @SerializedName("image") val image: String,
    @SerializedName("type") val type: String,
    @SerializedName("active") val active: Int,
    @SerializedName("name") val name: String
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
    @SerializedName("color") val color: String? = null
)