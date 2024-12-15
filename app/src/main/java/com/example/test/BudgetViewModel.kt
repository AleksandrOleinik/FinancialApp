package com.example.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class BudgetViewModel(private val repository: BudgetRepository) : ViewModel() {
    private val _budgets = MutableStateFlow<List<Budget>>(emptyList())
    val budgets: StateFlow<List<Budget>> = _budgets

    private val _totalBudget = MutableStateFlow(0)
    val totalBudget: StateFlow<Int> = _totalBudget

    init {
        viewModelScope.launch {
            repository.ensureDefaultBudget() // Ensure the default budget exists
            loadBudgets()
        }
    }

    private suspend fun loadBudgets() {
        val budgetList = repository.getAllBudgets()
        _budgets.value = budgetList

        // Calculate "Left" as Income - Sum of All Expenses
        val income = budgetList.find { it.category == "Income" }?.amount ?: 0
        val expenses = budgetList.filter { it.category != "Income" }.sumOf { it.amount }
        _totalBudget.value = income - expenses
    }

    fun increaseExpense(category: String, amount: Int) {
        viewModelScope.launch {
            if (category == "Income") {
                updateIncome(amount)
            } else {
                updateBudget(category, amount)
            }
        }
    }

    fun decreaseExpense(category: String, amount: Int) {
        viewModelScope.launch {
            if (category == "Income") {
                updateIncome(-amount)
            } else {
                updateBudget(category, -amount)
            }
        }
    }

    private suspend fun updateIncome(amountChange: Int) {
        val budgetList = repository.getAllBudgets() // Always fetch the latest data
        val incomeBudget = budgetList.find { it.category == "Income" }
        if (incomeBudget != null) {
            val updatedIncome = incomeBudget.copy(amount = incomeBudget.amount + amountChange)
            repository.insertBudget(updatedIncome) // Persist updated income
        } else {
            // Add Income if it does not exist
            repository.insertBudget(Budget(category = "Income", amount = amountChange))
        }
        loadBudgets() // Reload to reflect changes
    }

    private suspend fun updateBudget(category: String, amountChange: Int) {
        val budgetList = repository.getAllBudgets() // Always fetch the latest data
        val budgetToUpdate = budgetList.find { it.category == category }
        if (budgetToUpdate != null) {
            val updatedBudget = budgetToUpdate.copy(amount = budgetToUpdate.amount + amountChange)
            repository.insertBudget(updatedBudget) // Persist updated budget
        }
        loadBudgets() // Reload to reflect changes
    }

    fun resetBudgets() {
        viewModelScope.launch {
            repository.resetBudgets()
            loadBudgets()
        }
    }
}
