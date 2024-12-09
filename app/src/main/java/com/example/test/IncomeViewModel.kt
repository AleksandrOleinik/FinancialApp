
package com.example.test

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class IncomeViewModel : ViewModel() {
    private val repository = IncomeRepository()

    private val _incomeList = MutableLiveData<List<Income>>()
    val incomeList: LiveData<List<Income>> get() = _incomeList


    init{
        loadIncome()
    }
    fun loadIncome() {

        _incomeList.value = emptyList()
        _incomeList.value = repository.getAllIncome().toList()
    }

    fun addIncome(income: Income) {
        repository.addIncome(income)
        loadIncome()

        Log.d("IncomeDebug", "Income List = ${_incomeList.value}")

    }
}
