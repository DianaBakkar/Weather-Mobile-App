package com.example.weathermobileapp.userInterface.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathermobileapp.data.network.RepositoryProvider
import com.example.weathermobileapp.viewmodel.ViewModelFactory
import com.example.weathermobileapp.viewmodel.WeatherDataViewModel

@Composable
fun WeatherScreen(viewModel: WeatherDataViewModel = viewModel(factory = ViewModelFactory(RepositoryProvider.repository)), modifier: Modifier = Modifier) {
    var cityName by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Enter city name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.getWeather(cityName) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Weather")
        }

        Spacer(modifier = Modifier.height(16.dp))

        viewModel.weatherState.observeAsState().value?.let { weather ->
            Text("Temperature: ${weather.list[1].main.temp}\n" +
                    "Feels Like: ${weather.list[1].main.feelsLike}\n"+
                    "Min Temp: ${weather.list[1].main.tempMin}\n"+
                    "Max Temp: ${weather.list[1].main.tempMax}\n"+
                    "Pressure: ${weather.list[1].main.pressure}\n"+
                    "Sea Level: ${weather.list[1].main.seaLevel}\n"+
                    "Ground Level: ${weather.list[1].main.groundLevel}\n"+
                    "Humidity: ${weather.list[1].main.humidity}\n"

            )
        }

        viewModel.errorState.observeAsState().value?.let { error ->
            Text("Error: $error", color = MaterialTheme.colors.error)
        }
    }
}
