package com.example.test

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*


class BudgetViewModel(private val repository: BudgetRepository) : ViewModel() {

    private val _income = MutableStateFlow(repository.getIncome())
    val income: StateFlow<Int> = _income.asStateFlow()

    private val _budgetValues = MutableStateFlow(repository.getBudgetValues())
    val budgetValues: StateFlow<List<Int>> = _budgetValues.asStateFlow()

    val totalBudget: StateFlow<Int> = combine(_income, _budgetValues) { income, budget ->
        income - budget.sum()
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        repository.getIncome() - repository.getBudgetValues().sum()
    )

    val categories: List<String> = listOf("Housing", "Food", "Savings", "Other", "Fun")

    val colors: List<Color> = listOf(
        Color(0xFF78C257),
        Color(0xFFB5E48C),
        Color(0xFFE9FF70),
        Color(0xFFFFC107),
        Color(0xFFFFA726)
    )

    fun increaseIncome(amount: Int) {
        val newIncome = _income.value + amount
        repository.setIncome(newIncome)
        _income.value = newIncome
    }

    fun decreaseIncome(amount: Int) {
        val newIncome = _income.value - amount
        if (newIncome >= _budgetValues.value.sum()) {
            repository.setIncome(newIncome)
            _income.value = newIncome
        }
    }

    fun increaseExpense(index: Int, amount: Int) {
        val updatedBudget = _budgetValues.value.toMutableList().apply { this[index] += amount }
        repository.updateBudgetValue(index, updatedBudget[index])
        _budgetValues.value = updatedBudget
    }

    fun decreaseExpense(index: Int, amount: Int) {
        val updatedBudget = _budgetValues.value.toMutableList().apply {
            if (this[index] > 0) this[index] -= amount
        }
        repository.updateBudgetValue(index, updatedBudget[index])
        _budgetValues.value = updatedBudget
    }
}
