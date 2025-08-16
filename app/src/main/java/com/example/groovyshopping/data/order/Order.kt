package com.example.groovyshopping.data.order

import android.os.Parcelable
import com.example.groovyshopping.data.Address
import com.example.groovyshopping.data.CartProduct
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random.Default.nextLong

@Parcelize
data class Order(
    val orderStatus: String = "",
    val totalPrice: Float = 0f,
    val products: List<CartProduct> = emptyList(),
    val address: Address = Address(),
    val date: @RawValue Any? = null,
    val orderId: Long = 0L
) : Parcelable {

    fun getFormattedDate(): String {
        return when (date) {
            is com.google.firebase.Timestamp -> {
                SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date.toDate())
            }
            is String -> date
            else -> ""
        }
    }
}
