package com.example.groovyshopping.user.data.remote

data class DefaultResponse<T>(
    var code: Int,
    var status: Int,
    var message: String,
    var data: T
)

