package com.example.weathermobileapp.data.network

import com.example.weathermobileapp.model.CoordinatesResponse
import com.example.weathermobileapp.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("geo/1.0/direct")
    suspend fun getCoordinates(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): List<CoordinatesResponse>

    @GET("data/2.5/forecast")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): WeatherResponse
}

