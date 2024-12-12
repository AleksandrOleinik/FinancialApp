package com.example.test

class BudgetRepository {

    private var income: Int = 5000
    private var budgetValues: MutableList<Int> = mutableListOf(1000, 1000, 1000, 1000, 1000)

    fun getIncome(): Int {
        return income
    }

    fun setIncome(newIncome: Int) {
        income = newIncome
    }

    fun getBudgetValues(): List<Int> {
        return budgetValues.toList()
    }

    fun updateBudgetValue(index: Int, newValue: Int) {
        if (index in budgetValues.indices) {
            budgetValues[index] = newValue
        }
    }
}
