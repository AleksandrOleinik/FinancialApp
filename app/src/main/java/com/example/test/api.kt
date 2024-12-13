package com.example.test
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


data class OpenCloseResponse(
    val status: String,
    val from: String,
    val symbol: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double,
    val afterHours: Double?,
    val preMarket: Double?
)


interface PolygonApi {
    @GET("v1/open-close/{symbol}/{date}")
    suspend fun getDailyOpenClose(
        @Path("symbol") symbol: String,
        @Path("date") date: String,
        @Query("apiKey") apiKey: String
    ): OpenCloseResponse
}


