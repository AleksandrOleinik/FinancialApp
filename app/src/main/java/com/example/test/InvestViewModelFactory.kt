package com.example.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InvestViewModelFactory(private val repository: InvestmentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InvestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InvestViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
