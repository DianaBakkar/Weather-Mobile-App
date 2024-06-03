package com.example.weathermobileapp.model

import com.google.gson.annotations.SerializedName

data class CoordinatesResponse (
    @SerializedName("name") val name: String,
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double

)


