package com.example.test

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class InvestmentRepository(private val investmentDao: InvestmentDao) {

    val investments: Flow<List<Investment>> = investmentDao.getAllInvestments()

    suspend fun addInvestment(investment: Investment) {
        investmentDao.insertInvestment(investment)
    }

    suspend fun updateInvestmentPrice(ticker: String, newPrice: Double) {
        val investmentList = investmentDao.getAllInvestments().firstOrNull()
        val investment = investmentList?.find { it.ticker == ticker }
        if (investment != null) {
            val updatedInvestment = investment.copy(price = newPrice)
            investmentDao.updateInvestment(updatedInvestment)
        }
    }

    suspend fun deleteInvestmentByTicker(ticker: String) {
        val investmentList = investmentDao.getAllInvestments().firstOrNull()
        val investment = investmentList?.find { it.ticker == ticker }
        if (investment != null) {
            investmentDao.deleteInvestmentById(investment.id)
        }
    }

    suspend fun clearInvestments() {
        investmentDao.deleteAll()
    }
}
