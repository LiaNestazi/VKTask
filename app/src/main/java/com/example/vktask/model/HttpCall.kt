package com.example.vktask.model

data class HttpCall(
    var products: MutableList<Product>? = null,
    var total: Int? = null,
    var skip: Int? = null,
    var limit: Int? = null
)
