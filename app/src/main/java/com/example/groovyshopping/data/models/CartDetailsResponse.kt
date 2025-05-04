package com.homecookapp.user.data.models
import com.google.gson.annotations.SerializedName

data class CartDetailsResponse (

	@SerializedName("code") val code : Int,
	@SerializedName("status") val status : Int,
	@SerializedName("errors") val errors : String,
	@SerializedName("message") val message : String,
	@SerializedName("data") val data : CartDetailsData
)

data class CartDetailsData (

	@SerializedName("cart") val cart : List<Cart>,
	@SerializedName("store") val store : Store,
	@SerializedName("receive_from_store") val receive_from_store : Int,
	@SerializedName("payment_methods") var payment_methods : List<PaymentMethods>,
	@SerializedName("coupon") val coupon : Coupon,
	@SerializedName("discount") val discount : Double,
	@SerializedName("total_prices") val total_prices : Double,
	@SerializedName("store_tax") val store_tax : Double,
	@SerializedName("system_ratio") val system_ratio : Double,
	@SerializedName("tax") val tax : Double,
	@SerializedName("tax_details") val tax_details : Tax_details,
	@SerializedName("delivery_cost") val delivery_cost : Double,
	@SerializedName("grand_total") val grand_total : Double
)

data class Cart (

	@SerializedName("id") val id : Int,
	@SerializedName("user_id") val user_id : Int,
	@SerializedName("product_id") val product_id : Int,
	@SerializedName("store_id") val store_id : Int,
	@SerializedName("qty") var qty : Int,
	@SerializedName("note") val note : String,
	@SerializedName("total_price") val total_price : Double,
	@SerializedName("product") val product : Product,
	@SerializedName("options") val options : List<Options>
)

data class Coupon (

	@SerializedName("coupon") val coupon : String,
	@SerializedName("is_available") val is_available : Boolean,
	@SerializedName("msg") val msg : String,
	@SerializedName("discount") val discount : Double
)

data class Item (

	@SerializedName("id") val id : Int,
	@SerializedName("product_id") val product_id : Int,
	@SerializedName("option_id") val option_id : Int,
	@SerializedName("title_ar") val title_ar : String,
	@SerializedName("title_en") val title_en : String,
	@SerializedName("price") val price : Double,
	@SerializedName("title") val title : String
)

data class Option (

	@SerializedName("id") val id : Int,
	@SerializedName("is_multi") val is_multi : Int,
	@SerializedName("product_id") val product_id : Int,
	@SerializedName("title_ar") val title_ar : String,
	@SerializedName("title_en") val title_en : String,
	@SerializedName("title") val title : String,
	@SerializedName("is_required") val is_required : Int
)
data class Order_conditions (

	@SerializedName("id") val id : Int,
	@SerializedName("store_id") val store_id : Int,
	@SerializedName("title_ar") val title_ar : String,
	@SerializedName("title_en") val title_en : String,
	@SerializedName("text_ar") val text_ar : String,
	@SerializedName("text_en") val text_en : String,
	@SerializedName("title") val title : String,
	@SerializedName("text") val text : String
)
data class Options (

	@SerializedName("id") val id : Int,
	@SerializedName("order_id") val order_id : Int,
	@SerializedName("cart_id") val cart_id : Int,
	@SerializedName("option_id") val option_id : Int,
	@SerializedName("option_item_id") val option_item_id : Int,
	@SerializedName("option") val option : Option,
	@SerializedName("item") val item : Item
)
data class PaymentMethods (

	@SerializedName("id") val id : Int,
	@SerializedName("image") val image : String,
	@SerializedName("type") val type : String,
	@SerializedName("active") val active : Int,
	@SerializedName("name") val name : String,
	var isSelected:Boolean
)
data class Product (

	@SerializedName("id") val id : Int,
	@SerializedName("store_id") val store_id : Int,
	@SerializedName("image") val image : String,
	@SerializedName("name_ar") val name_ar : String,
	@SerializedName("name_en") val name_en : String,
	@SerializedName("price") val price : Double,
	@SerializedName("enable_stock") val enable_stock : Int,
	@SerializedName("stock") val stock : Int,
	@SerializedName("name") val name : String,
	@SerializedName("description") val description : String,
	@SerializedName("is_favourite") val is_favourite : Int,
	@SerializedName("price_after_offer") val price_after_offer : Double
)

data class Store (

	@SerializedName("id") val id : Int,
	@SerializedName("name_ar") val name_ar : String,
	@SerializedName("name_en") val name_en : String,
	@SerializedName("image") val image : String,
	@SerializedName("auto_accept_orders") val auto_accept_orders : Int,
	@SerializedName("status") val status : Int,
	@SerializedName("delivery_cost") val delivery_cost : Double,
	@SerializedName("delivery_time") val delivery_time : Double,
	@SerializedName("is_delivered_from_store") val is_delivered_from_store : Int,
	@SerializedName("wallet") val wallet : Double,
	@SerializedName("ratio_value") val ratio_value : Double,
	@SerializedName("is_ratio_percentage") val is_ratio_percentage : Double,
	@SerializedName("tax") val tax : Double,
	@SerializedName("user_id") val user_id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("description") val description : String,
	@SerializedName("user_distance") val user_distance : Int,
	@SerializedName("is_available") val is_available : Int,
	@SerializedName("is_favourite") val is_favourite : Int,
	@SerializedName("order_policy") val order_policy : String,
	@SerializedName("order_conditions") val order_conditions : List<Order_conditions>
)

data class Tax_details (

	@SerializedName("store_tax") val store_tax : Double,
	@SerializedName("system_ratio") val system_ratio : Double,
	@SerializedName("total_taxes") val total_taxes : Double
)