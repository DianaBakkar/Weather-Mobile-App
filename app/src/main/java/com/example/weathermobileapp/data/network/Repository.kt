package com.example.weathermobileapp.data.network

import RetrofitClient
import com.example.weathermobileapp.model.CoordinatesResponse
import com.example.weathermobileapp.model.WeatherResponse

interface Repository {
    suspend fun getCoordinates(cityName: String): CoordinatesResponse
    suspend fun getWeather(lat: Double, lon: Double): WeatherResponse
}

object RepositoryProvider {
    val repository: Repository by lazy {
        RepositoryImpl()
    }
}

class RepositoryImpl : Repository {
    private val apiService: ApiService = RetrofitClient.api
    private val apiKey = "d27096a7c05097bfc12a943fb9b56237"

    override suspend fun getCoordinates(cityName: String): CoordinatesResponse {
        return apiService.getCoordinates(cityName, apiKey).first()
    }

    override suspend fun getWeather(lat: Double, lon: Double): WeatherResponse {
        return apiService.getWeather(lat, lon, apiKey)
    }
}
