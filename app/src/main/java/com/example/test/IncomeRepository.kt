package com.example.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IncomeRepository(private val incomeDao: IncomeDao) {

    suspend fun getAllIncome(): List<Income> {
        return withContext(Dispatchers.IO) {
            incomeDao.getAllIncomes()
        }
    }

    suspend fun addIncome(income: Income) {
        withContext(Dispatchers.IO) {
            incomeDao.insertIncome(income)
        }
    }

    suspend fun deleteIncome(incomeId: Int) {
        withContext(Dispatchers.IO) {
            incomeDao.deleteIncome(incomeId)
        }
    }
}
