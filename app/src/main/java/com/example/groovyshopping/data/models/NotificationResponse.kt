package com.example.groovyshopping.user.data.models

import com.google.gson.annotations.SerializedName


data class NotificationResponse(

    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: NotificationData
)

data class NotificationData(

    @SerializedName("today") val today: List<NotificationListData>,
    @SerializedName("yesterday") val yesterday: List<NotificationListData>,
    @SerializedName("before") val before: List<NotificationListData>
)

data class NotificationListData(

    @SerializedName("id") val id: String,
    @SerializedName("notifiable_id") val notifiable_id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
    @SerializedName("action_type") val action_type: String,
    @SerializedName("action_id") val action_id: String,
    @SerializedName("image") val image: String,
    @SerializedName("read_at") val read_at: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("created_at_human_format") val created_at_human_format: String
)