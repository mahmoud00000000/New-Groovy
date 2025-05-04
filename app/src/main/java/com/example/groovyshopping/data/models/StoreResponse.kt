package com.homecookapp.user.data.models

import com.google.gson.annotations.SerializedName

data class AllStoreResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: StorePagingResponse
)
data class StoreDetailsResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: StoreDataModel
)
data class StorePagingResponse (
    @SerializedName("total") val total : Int,
    @SerializedName("count") val count : Int,
    @SerializedName("per_page") val per_page : Int,
    @SerializedName("current_page") val current_page : Int,
    @SerializedName("total_pages") val total_pages : Int,
    @SerializedName("last_page") val last_page : Int,
    @SerializedName("data") val data : List<StoreDataModel>
)
data class StoreDataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("is_favourite") val is_favourite: Int,
    @SerializedName("is_available") val is_available: Int,
    @SerializedName("name") val name: String,
    @SerializedName("name_ar") val name_ar: String,
    @SerializedName("name_en") val name_en: String,
    @SerializedName("description") val description: String,
    @SerializedName("description_ar") val description_ar: String,
    @SerializedName("description_en") val description_en: String,
    @SerializedName("price_range") val price_range: Int,
    @SerializedName("delivery_time") val delivery_time: DeliveryTimeDataModel,
    @SerializedName("type") val type: String,
    @SerializedName("image") val image: String,
    @SerializedName("rate") val rate: Double,
    @SerializedName("available") val available: Int,
    @SerializedName("active") val active: Int,
    @SerializedName("order") val order: Int,
    @SerializedName("user") val user: UserDataModel,
    @SerializedName("categories") val categories: List<CategoryDataModel>,
    @SerializedName("tags") val tags: List<TagDataModel>
)


data class CategoryDataModel (

    @SerializedName("id") val id : Int,
    @SerializedName("name_en") val name_en : String,
    @SerializedName("name_ar") val name_ar : String,
    @SerializedName("image") val image : String,
    @SerializedName("name") val name : String,
    @SerializedName("pivot") val pivot : Pivot
)
data class Pivot (

    @SerializedName("store_id") val store_id : Int,
    @SerializedName("tag_id") val tag_id : Int
)
data class TagDataModel (

    @SerializedName("id") val id : Int,
    @SerializedName("name_en") val name_en : String,
    @SerializedName("name_ar") val name_ar : String,
    @SerializedName("icon") val icon : String,
    @SerializedName("name") val name : String,
    @SerializedName("pivot") val pivot : Pivot
)
data class DeliveryTimeDataModel (

    @SerializedName("from") val from : Int,
    @SerializedName("to") val to : Int,
    @SerializedName("type") val type : String
)