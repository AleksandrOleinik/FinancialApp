package com.example.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExpenseViewModel(private val repository: ExpenseRepository) : ViewModel() {

    private val _expenseList = MutableStateFlow<List<Expense>>(emptyList())
    val expenseList: StateFlow<List<Expense>> = _expenseList

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            _expenseList.value = repository.getAllExpenses()
        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repository.addExpense(expense)
            loadExpenses()
        }
    }

    fun deleteExpense(expenseId: Int) {
        viewModelScope.launch {
            repository.deleteExpense(expenseId)
            loadExpenses()
        }
    }
}
