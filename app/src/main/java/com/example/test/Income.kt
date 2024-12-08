package com.example.test

import java.time.LocalDate

data class Income (
    val id: Int,
    val date: LocalDate,
    val amount: Double,
    val cur: String,
)