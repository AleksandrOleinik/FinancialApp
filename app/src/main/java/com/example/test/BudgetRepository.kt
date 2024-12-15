package com.example.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val amount: Int
)

class BudgetRepository(private val budgetDao: BudgetDao) {
    suspend fun getAllBudgets(): List<Budget> {
        return withContext(Dispatchers.IO) {
            budgetDao.getAllBudgets()
        }
    }

    suspend fun insertBudget(budget: Budget) {
        withContext(Dispatchers.IO) {
            budgetDao.insertBudget(budget)
        }
    }

    suspend fun ensureDefaultBudget() {
        withContext(Dispatchers.IO) {
            if (budgetDao.getAllBudgets().isEmpty()) {
                // Add Income and Expense categories
                budgetDao.insertBudget(Budget(category = "Income", amount = 5000))
                budgetDao.insertBudget(Budget(category = "Housing", amount = 1000))
                budgetDao.insertBudget(Budget(category = "Food", amount = 1000))
                budgetDao.insertBudget(Budget(category = "Savings", amount = 1000))
                budgetDao.insertBudget(Budget(category = "Other", amount = 1000))
                budgetDao.insertBudget(Budget(category = "Fun", amount = 1000))
            }
        }
    }

    suspend fun resetBudgets() {
        withContext(Dispatchers.IO) {
            budgetDao.resetBudgets()
            ensureDefaultBudget() // Reset includes adding defaults
        }
    }
}

