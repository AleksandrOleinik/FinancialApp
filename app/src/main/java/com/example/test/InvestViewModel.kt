package com.example.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class InvestViewModel : ViewModel() {

    private val repository = InvestmentRepository()

    val investments: StateFlow<List<InvestEntry>> = repository.investments

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

                    val yesterday = LocalDate.now().minusDays(1)
                    val dateString = yesterday.format(DateTimeFormatter.ISO_DATE)

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
        repository.addInvestment(entry)
    }
    fun deleteInvestment(entry: InvestEntry) {
        repository.deleteInvestment(entry)
    }


}
