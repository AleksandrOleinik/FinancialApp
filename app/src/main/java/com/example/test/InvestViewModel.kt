package com.example.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.DayOfWeek

class InvestViewModel(private val repository: InvestmentRepository) : ViewModel() {


    val investments: StateFlow<List<InvestEntry>> = repository.investments
        .map { investments ->
            investments.map { investment ->
                InvestEntry(
                    type = investment.type,
                    ticker = investment.ticker,
                    boughtAt = investment.boughtAt,
                    amount = investment.amount,
                    price = investment.price
                )
            }
        }
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, emptyList())

    private val apiKey = BuildConfig.API_KEY
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.polygon.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val polygonApi = retrofit.create(PolygonApi::class.java)


    fun fetchAndUpdatePrice(ticker: String, type: String, boughtAt: Double) {
        println("Fetching price for $type")
        viewModelScope.launch {
            val newPrice: Double
            if (type.equals("Crypto", ignoreCase = true) || type.equals("Stock", ignoreCase = true)) {
                try {
                    val adjustedTicker = if (type.equals("Crypto", ignoreCase = true)) {
                        "X:${ticker.uppercase()}USD"
                    } else {
                        ticker.uppercase()
                    }

                    val lastBusinessDay = getLastBusinessDay()
                    val dateString = lastBusinessDay.format(DateTimeFormatter.ISO_DATE)
                    println("Ticker: $adjustedTicker")
                    println("Date: $dateString")
                    val apiResponse = polygonApi.getDailyOpenClose(adjustedTicker, dateString, apiKey)
                    newPrice = apiResponse.close
                    println("apiResponse: $apiResponse")


                    repository.updateInvestmentPrice(ticker, newPrice)
                } catch (e: Exception) {
                    println("Error fetching price for $ticker: ${e.message}")
                }
            } else {
                newPrice = boughtAt
                repository.updateInvestmentPrice(ticker, newPrice)
            }
        }
    }


    fun addInvestment(entry: InvestEntry) {
        viewModelScope.launch {
            val investment = Investment(
                type = entry.type,
                ticker = entry.ticker,
                boughtAt = entry.boughtAt,
                amount = entry.amount,
                price = entry.price
            )
            repository.addInvestment(investment)
        }
    }


    fun deleteInvestment(entry: InvestEntry) {
        viewModelScope.launch {
            repository.deleteInvestmentByTicker(entry.ticker)
        }
    }

    fun getLastBusinessDay(): LocalDate {
        var date = LocalDate.now().minusDays(1)


        while (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY) {
            date = date.minusDays(1)
        }
        return date
    }
}
