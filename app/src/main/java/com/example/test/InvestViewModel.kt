package com.example.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.test.BuildConfig


class InvestViewModel : ViewModel() {

    private val _response = mutableStateOf<OpenCloseResponse?>(null)
    val response: State<OpenCloseResponse?> = _response


    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.polygon.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val polygonApi = retrofit.create(PolygonApi::class.java)


    private val apiKey = BuildConfig.API_KEY


    fun fetchDailyOpenClose(symbol: String, date: String) {
        viewModelScope.launch {
            try {
                val apiResponse = polygonApi.getDailyOpenClose(symbol, date, apiKey)
                _response.value = apiResponse
            } catch (e: Exception) {
                _errorMessage.value = e.message
                println("Error: ${e.message}")
            }
        }
    }
}
