package com.example.groovyshopping.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

    @Parcelize
    data class Product(
        val id: String = "",
        val name: String = "",
        val category: String = "",
        val price: Float = 0f,
        val offerPercentage: Float? = null,
        val description: String? = null,
        val colors: List<Int> = emptyList(),
        val sizes: List<String> = emptyList(),
        val images: List<String> = emptyList()
    ):  Parcelable {
        constructor():this("0","","",0f,images = emptyList())
    }
