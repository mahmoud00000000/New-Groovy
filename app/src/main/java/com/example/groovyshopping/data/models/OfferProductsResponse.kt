package com.example.groovyshopping.user.data.models
import com.google.gson.annotations.SerializedName

data class OfferProductsResponse (

	@SerializedName("code") val code : Int,
	@SerializedName("status") val status : Int,
	@SerializedName("errors") val errors : String,
	@SerializedName("message") val message : String,
	@SerializedName("data") val data : OfferProductsData
)

data class OfferProductsData (

	@SerializedName("current_page") val current_page : Int,
	@SerializedName("data") val offerDataList : List<OfferDataList>,
	@SerializedName("first_page_url") val first_page_url : String,
	@SerializedName("from") val from : Int,
	@SerializedName("last_page") val last_page : Int,
	@SerializedName("last_page_url") val last_page_url : String,
	@SerializedName("links") val links : List<Links>,
	@SerializedName("next_page_url") val next_page_url : String,
	@SerializedName("path") val path : String,
	@SerializedName("per_page") val per_page : Int,
	@SerializedName("prev_page_url") val prev_page_url : String,
	@SerializedName("to") val to : Int,
	@SerializedName("total") val total : Int
)

data class OfferDataList (

	@SerializedName("id") val id : Int,
	@SerializedName("store_id") val store_id : Int,
	@SerializedName("product_id") val product_id : Int,
	@SerializedName("value") val value : Int,
	@SerializedName("is_percentage") val is_percentage : Int,
	@SerializedName("active") val active : Int,
	@SerializedName("start_at") val start_at : String,
	@SerializedName("end_at") val end_at : String,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("product") val product : Product
)

data class Links (

	@SerializedName("url") val url : String,
	@SerializedName("label") val label : String,
	@SerializedName("active") val active : Boolean
)
data class ResultData(
	@SerializedName("is_success") var isSuccess: Boolean? = null,
	@SerializedName("wallet") var wallet: Double? = null
)
