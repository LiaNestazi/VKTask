package com.example.vktask.retrofit

object Common {
    private val URL = "https://dummyjson.com/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(URL).create(RetrofitServices::class.java)
}