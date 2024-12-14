package com.example.test

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InvestmentRepository {

    private val _investments = MutableStateFlow<List<InvestEntry>>(emptyList())
    val investments: StateFlow<List<InvestEntry>> = _investments

    fun addInvestment(entry: InvestEntry) {
        _investments.value = _investments.value + entry
    }

    fun updateInvestmentPrice(ticker: String, newPrice: Double) {
        _investments.value = _investments.value.map {
            if (it.ticker == ticker) it.copy(price = newPrice) else it
        }
    }

    fun deleteInvestment(entry: InvestEntry) {
        _investments.value = _investments.value.filter { it != entry }
    }

}
