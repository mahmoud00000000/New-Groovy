package com.example.groovyshopping.data.models

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName

data class MorePagesDataModel(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") var pages: MoreDataModel,
)
data class MoreDataModel(
    @SerializedName("pages") var pages: ArrayList<MorePagesData> = arrayListOf(),
    @SerializedName("socials") var socials: ArrayList<MoreSocialsDataModel> = arrayListOf()
)
data class PageDataModel(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: MorePagesData
)

data class MorePagesData(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("image") val image: String? = null,
    @DrawableRes val drawableImg: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("name_ar") var name_ar: String? = null,
    @SerializedName("value") var value: String? = null,
    @SerializedName("text_ar") var text_ar: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("text") val text: String? = null,
    @SerializedName("url") val url: String? = null
)

data class MoreSocialsDataModel(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name_en") var nameEn: String? = null,
    @SerializedName("name_ar") var nameAr: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("key") var key: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("value") var value: String? = null,
    @SerializedName("active") var active: Int? = null,
    @SerializedName("deleted_at") var deletedAt: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("name") var name: String? = null
)