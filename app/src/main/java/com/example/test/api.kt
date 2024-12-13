package com.example.test

import android.content.Context
import java.util.Properties
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class OpenCloseResponse(
    val symbol: String,
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double,
    val volume: Long,
    val afterHours: Double?,
    val preMarket: Double?,
    val from: String
)

interface PolygonApi {
    @GET("v1/open-close/{symbol}/{date}")
    suspend fun getDailyOpenClose(
        @Path("symbol") symbol: String,
        @Path("date") date: String,
        @Query("apiKey") apiKey: String
    ): OpenCloseResponse
}

class Api(private val context: Context) {

    private val dotenv: Properties = Properties().apply {
        val inputStream = context.resources.openRawResource(R.raw.env)
        load(inputStream.reader())
    }
    private val apiKey: String = dotenv.getProperty("API_KEY")
        ?: throw IllegalArgumentException("API_KEY not found in .env file")

    fun testApi() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getDailyOpenClose(
                    symbol = "AAPL",
                    date = "2024-12-12",
                    apiKey = apiKey
                )
                println("Close Price: ${response.close}")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}



