package com.example.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    suspend fun getAllExpenses(): List<Expense> {
        return withContext(Dispatchers.IO) {
            expenseDao.getAllExpenses()
        }
    }

    suspend fun addExpense(expense: Expense) {
        withContext(Dispatchers.IO) {
            expenseDao.insertExpense(expense)
        }
    }

    suspend fun deleteExpense(expenseId: Int) {
        withContext(Dispatchers.IO) {
            expenseDao.deleteExpense(expenseId)
        }
    }
}
