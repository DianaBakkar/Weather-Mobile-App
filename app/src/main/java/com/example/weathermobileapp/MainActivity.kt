package com.example.weathermobileapp
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weathermobileapp.data.network.RepositoryProvider
import com.example.weathermobileapp.viewmodel.ViewModelFactory
import com.example.weathermobileapp.viewmodel.WeatherDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val repository = RepositoryProvider.repository
    private lateinit var cityNameEditText: EditText
    private lateinit var fetchButton: Button
    private lateinit var weatherTextView: TextView
    private lateinit var weatherViewModel: WeatherDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create UI elements programmatically
        cityNameEditText = EditText(this).apply {
            hint = "Enter city name"
        }

        fetchButton = Button(this).apply {
            text = "Fetch Weather"
            setOnClickListener {
                val cityName = cityNameEditText.text.toString()
                if (cityName.isNotBlank()) {
                    fetchWeatherData(cityName)
                }
            }
        }

        weatherTextView = TextView(this).apply {
            text = "Weather data will be displayed here"
            textSize = 18f
        }

        // Add UI elements to the layout
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(cityNameEditText)
            addView(fetchButton)
            addView(weatherTextView)
        }

        // Set the layout as the content view
        setContentView(layout)

        // Initialize ViewModel using ViewModelFactory
        val factory = ViewModelFactory(repository)
        weatherViewModel = ViewModelProvider(this, factory).get(WeatherDataViewModel::class.java)
    }

    private fun fetchWeatherData(cityName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val coordinates = repository.getCoordinates(cityName)
                val weather = repository.getWeather(coordinates.latitude, coordinates.longitude)
                withContext(Dispatchers.Main) {
                    weatherTextView.text = "Weather in ${coordinates.name}: ${weather.main.temperature}°C"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    weatherTextView.text = "Failed to get weather data"
                }
            }
        }
    }
}
