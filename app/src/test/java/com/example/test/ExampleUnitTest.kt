package com.example.test

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PolygonApi {
    @GET("v1/open-close/{symbol}/{date}")
    suspend fun getDailyOpenClose(
        @Path("symbol") symbol: String,
        @Path("date") date: String,
        @Query("apiKey") apiKey: String
    ): PolygonResponse
}

data class PolygonResponse(
    val symbol: String?,
    val open: Double?,
    val high: Double?,
    val low: Double?,
    val close: Double?,
    val volume: Long?
)


class ExampleUnitTest {

    // Define the Retrofit instance and API interface
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.polygon.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val polygonApi = retrofit.create(PolygonApi::class.java)

    // Replace with your actual API key
    private val apiKey = "ry8MiE5_EnKF6bxEoQ1QRVPtI_VjXxB3"

    @Test
    fun testGetDailyOpenClose() = runBlocking {
        try {
            // Fetch data from the API for AAPL on a specific date
            val response = polygonApi.getDailyOpenClose(
                symbol = "AAPL",
                date = "2024-12-12",
                apiKey = apiKey
            )

            // Print the close price to the console
            println("Close Price for AAPL on 2024-12-12: ${response.close}")

            // Assert that the response contains a non-null close price
            assertNotNull("Close price should not be null", response.close)
        } catch (e: Exception) {
            // If an error occurs, fail the test
            println("Error: ${e.message}")
            throw e
        }
    }
}
