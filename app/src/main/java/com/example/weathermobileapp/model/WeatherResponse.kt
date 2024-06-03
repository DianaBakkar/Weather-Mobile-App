package com.example.weathermobileapp.model

import com.google.gson.annotations.SerializedName



data class WeatherResponse(
    @SerializedName("main") val main: Main
) {
    data class Main(
        @SerializedName("temp") val temperature: Float
    )
}
