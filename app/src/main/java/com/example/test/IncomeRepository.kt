package com.example.test
import java.time.LocalDate

class IncomeRepository {
    private val incomesList = mutableListOf(
            Income(1, LocalDate.of(2023, 10, 12), 5000.0, "USD"),
            Income(2, LocalDate.of(2023, 11, 13), 3000.0, "USD"))


    fun getAllIncome(): List<Income> {
        return incomesList.toList()
    }

    fun addIncome(income: Income) {
            incomesList.add(income)
    }

}