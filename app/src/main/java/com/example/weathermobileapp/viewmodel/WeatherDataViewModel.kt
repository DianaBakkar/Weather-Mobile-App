package com.example.weathermobileapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermobileapp.data.network.Repository
import com.example.weathermobileapp.model.WeatherResponse
import kotlinx.coroutines.launch

class WeatherDataViewModel(private val repository: Repository) : ViewModel() {

    private val _weatherState = MutableLiveData<WeatherResponse>()
    val weatherState: LiveData<WeatherResponse> = _weatherState

    private val _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun getWeather(cityName: String) {
        viewModelScope.launch {
            try {
                val coordinates = repository.getCoordinates(cityName)
                val weather = repository.getWeather(coordinates.latitude, coordinates.longitude)
                _weatherState.value = weather
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }
}
