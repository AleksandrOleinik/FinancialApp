package com.example.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IncomeViewModel(private val repository: IncomeRepository) : ViewModel() {

    private val _incomeList = MutableStateFlow<List<Income>>(emptyList())
    val incomeList: StateFlow<List<Income>> = _incomeList

    init {
        loadIncomes()
    }

    private fun loadIncomes() {
        viewModelScope.launch {
            _incomeList.value = repository.getAllIncome()
        }
    }

    fun addIncome(income: Income) {
        viewModelScope.launch {
            repository.addIncome(income)
            loadIncomes()
        }
    }

    fun deleteIncome(incomeId: Int) {
        viewModelScope.launch {
            repository.deleteIncome(incomeId)
            loadIncomes()
        }
    }
}
