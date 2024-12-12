package com.example.test

import java.time.LocalDate

class ExpenseRepository {
    private val expensesList = mutableListOf(
        Expense(1, LocalDate.of(2023, 10, 12), 200.0, "USD"),
        Expense(2, LocalDate.of(2023, 11, 13), 150.0, "USD")
    )

    fun getAllExpenses(): List<Expense> {
        return expensesList.toList()
    }

    fun addExpense(expense: Expense) {
        expensesList.add(expense)
    }
}
