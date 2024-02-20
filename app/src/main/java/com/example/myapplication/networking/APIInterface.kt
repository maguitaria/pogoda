package com.example.myapplication.networking
import com.example.myapplication.model.CurrentWeatherResponse
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    // Get current weather data
    @GET("current.json")
    fun getCurrentWeather(
        @Query("key") key: String? = ApiConfig.API_KEY,
        @Query("q") city: String,
        @Query("aqi") aqi: String = "no" // air quality data
    ) : Call <CurrentWeatherResponse>
}