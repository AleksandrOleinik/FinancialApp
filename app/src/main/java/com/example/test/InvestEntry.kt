package com.example.test

data class InvestEntry(
    val type: String,
    val ticker: String,
    var boughtAt: Double,
    var amount: Double,
    var price: Double = 0.0
)