package com.abora.perfectobase.data.models

import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(

    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val productDetails: ProductDetails
)

data class Category (

    @SerializedName("id") val id : Int,
    @SerializedName("name_ar") val name_ar : String,
    @SerializedName("name_en") val name_en : String,
    @SerializedName("image") val image : String,
    @SerializedName("name") val name : String
)

data class ProductDetails (

    @SerializedName("id") val id : Int,
    @SerializedName("store_id") val store_id : Int,
    @SerializedName("image") val image : String,
    @SerializedName("name_ar") val name_ar : String,
    @SerializedName("name_en") val name_en : String,
    @SerializedName("name") val name : String,
    @SerializedName("price") val price : Double,
    @SerializedName("price_after_offer") val price_after_offer : Double,
    @SerializedName("enable_stock") val enable_stock : Int,
    @SerializedName("stock") val stock : Int,
    @SerializedName("is_favourite") var is_favourite : Int,
    @SerializedName("description_ar") val description_ar : String,
    @SerializedName("description_en") val description_en : String,
    @SerializedName("description") val description : String,
    @SerializedName("serial_num") val serial_num : String,
    @SerializedName("category_id") val category_id : Int,
    @SerializedName("active") val active : Int,
    @SerializedName("cart_count") val cart_count : Int,
    @SerializedName("category") val category : Category,
    @SerializedName("store") val store : Store,
    @SerializedName("options") var options : List<Options>,
    @SerializedName("offer") val offer : Offer
)

data class OptionsItems (

    @SerializedName("id") val id : Int,
    @SerializedName("product_id") val product_id : Int,
    @SerializedName("option_id") val option_id : Int,
    @SerializedName("title_ar") val title_ar : String,
    @SerializedName("title_en") val title_en : String,
    @SerializedName("price") val price : Int,
    @SerializedName("title") val title : String,
    var isSelected: Boolean = false
)

data class Offer (
    @SerializedName("id") val id : Int,
    @SerializedName("store_id") val store_id : Int,
    @SerializedName("product_id") val product_id : Int,
    @SerializedName("value") val value : Int,
    @SerializedName("is_percentage") val is_percentage : Int,
    @SerializedName("active") val active : Int,
    @SerializedName("start_at") val start_at : String,
    @SerializedName("end_at") val end_at : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)

data class Options (

    @SerializedName("id") val id : Int,
    @SerializedName("is_multi") val is_multi : Int,
    @SerializedName("product_id") val product_id : Int,
    @SerializedName("title_ar") val title_ar : String,
    @SerializedName("title_en") val title_en : String,
    @SerializedName("title") val title : String,
    @SerializedName("is_required") val is_required : Int,
    @SerializedName("items") var items : List<OptionsItems>,
    var haveSelectedItem: Boolean=false
)

data class OrderConditions (
    @SerializedName("id") val id : Int,
    @SerializedName("store_id") val store_id : Int,
    @SerializedName("title_ar") val title_ar : String,
    @SerializedName("title_en") val title_en : String,
    @SerializedName("text_ar") val text_ar : String,
    @SerializedName("text_en") val text_en : String,
    @SerializedName("title") val title : String,
    @SerializedName("text") val text : String
)


data class Store (
    @SerializedName("id") val id : Int,
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("name_ar") val name_ar : String,
    @SerializedName("name_en") val name_en : String,
    @SerializedName("rate") val rate : Double,
    @SerializedName("min_per_order") val min_per_order : Int,
    @SerializedName("order_policy_ar") val order_policy_ar : String,
    @SerializedName("order_policy_en") val order_policy_en : String,
    @SerializedName("name") val name : String,
    @SerializedName("description") val description : String,
    @SerializedName("user_distance") val user_distance : Int,
    @SerializedName("is_available") val is_available : Int,
    @SerializedName("is_favourite") val is_favourite : Int,
    @SerializedName("order_policy") val order_policy : String,
    @SerializedName("order_conditions") val orderConditions : List<OrderConditions>
)