package com.example.test

import java.time.LocalDate

data class Expense(
    val id: Int,
    val date: LocalDate,
    val amount: Double,
    val cur: String
)
