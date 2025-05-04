package com.example.groovyshopping.user.data.models

import com.google.gson.annotations.SerializedName
import com.example.groovyshopping.user.data.models.OfferDataList
import com.example.groovyshopping.user.data.models.StoreDataModel
import com.example.groovyshopping.user.data.models.UserDataModel

data class HomeResponse(
    var code: Int? = 0,
    var `data`: List<HomeDataModel>? = null,
    var errors: Any? = "",
    var message: String? = "",
    var status: Int? = 0
)
data class StoreResponse(
    var code: Int? = 0,
    var `data`: HomeDataItem? = null,
    var errors: Any? = "",
    var message: String? = "",
    var status: Int? = 0
)

data class MoreResponse(
    var code: Int? = 0,
    var `data`: MoreDataModel? = null,
    var errors: Any? = "",
    var message: String? = "",
    var status: Int? = 0
)

data class MoreDataModel(
    var code: Int? = 0,
    var `data`: List<HomeDataItem>? = null,
    var errors: Any? = "",
    var message: String? = "",
    var status: Int? = 0
)

data class HomeDataModel(
    @SerializedName("url") val url: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("type") val type: String,
    @SerializedName("count") val count: Int,
    @SerializedName("display") val display: String,
    @SerializedName("data") val data: List<HomeDataItem>
)

data class HomeDataItem(
    @SerializedName("id") var id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("name_ar") val name_ar: String,
    @SerializedName("name_en") val name_en: String,
    @SerializedName("description_ar") val description_ar: String,
    @SerializedName("dark_bg_color") val dark_bg_color: String,
    @SerializedName("white_bg_color") val white_bg_color: String,
    @SerializedName("description_en") val description_en: String,
    @SerializedName("image") val image: String,
    @SerializedName("action") val action: String,
    @SerializedName("action_id") val action_id: String,
    @SerializedName("cover") val cover: String,
    @SerializedName("rate") val rate: String,
    @SerializedName("price") val price: Double,
    @SerializedName("price_after_offer") val price_after_offer: Double,
    @SerializedName("user_distance") val user_distance: String,
    @SerializedName("is_available") val is_available: Int,
    @SerializedName("city_id") val city_id: Int,
    @SerializedName("district_id") val district_id: Int,
    @SerializedName("country_id") val country_id: Int,
    @SerializedName("active") val active: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("enable_stock") val enable_stock: Int,
    @SerializedName("stock") val stock: Int,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("subcategory_id") val subcategory_id: Int,
    @SerializedName("category_id") val category_id: Int,
    @SerializedName("rates_count") val rates_count: Int,
    @SerializedName("is_favourite") var is_favourite: Int,
    @SerializedName("delivery_cost") val delivery_cost: String,
    @SerializedName("delivery_time") val delivery_time: String,
    @SerializedName("min_per_order") val min_per_order: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("name") var name: String,
    @SerializedName("description") val description: String,
    @SerializedName("country") val country: CountryDataModel?,
    @SerializedName("city") val city: CountryDataModel?,
    @SerializedName("district") val district: CountryDataModel?,
    @SerializedName("user") val user: UserDataModel?,
    @SerializedName("store") val store: StoreDataModel?,
    @SerializedName("offer") val offer: OfferDataList?,
    @SerializedName("subcategories") val subcategories: List<HomeDataItem>,
    @SerializedName("categories") val categories: List<HomeDataItem>,
    @SerializedName("products") val products: List<HomeDataItem>,
    var selected: Boolean = false
)
