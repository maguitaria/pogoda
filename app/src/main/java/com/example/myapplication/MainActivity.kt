package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.composeweatherapp.core.designsystem.theme.ComposeWeatherAppTheme
import com.example.myapplication.model.CurrentWeatherResponse
import com.example.myapplication.viewmodel.MainViewModel

// https://medium.com/@dimaswisodewo98/fetch-data-from-api-in-android-studio-kotlin-using-retrofit-with-mvvm-architecture-4f6b673f6a28
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            ComposeWeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherApp()
                }

            }
        }

    }

    @Preview
    @Composable
    fun WeatherApp() {
        mainViewModel = MainViewModel()
        subscribe()

        var cityName by remember { mutableStateOf("") }
        var cityNameInput by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Header()
            Spacer(modifier = Modifier.height(16.dp))

            // Search Field for City Name
            OutlinedTextField(
                value = cityName,
                onValueChange = { cityName = it },
                label = { Text(text = "Enter your city") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
            )
//TODO add handling of special characters sending to API request

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (cityName.isNotBlank()) {
                        mainViewModel.getWeatherData(cityName)
                        cityNameInput = cityName

                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                enabled = cityName.isNotBlank()
            ) {
                Icon(Icons.Default.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Send Request")
            }

            // Weather result
            Text(
                text = "Weather Result",
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            val weatherData by mainViewModel.weatherData.observeAsState(null)
            weatherData?.let {
                CoilImage(it)
                setResultText(it)

            }

        }
    }

    // New function for CoilImage
    @Composable
    private fun CoilImage(weatherData: CurrentWeatherResponse) {
        // Get the icon URL from weather data
        val iconUrl = "https:${weatherData.current?.condition?.icon}"
        println(iconUrl)
        // Create a white rounded background for the icon
        Box(
            modifier = Modifier
                .size(120.dp) // Adjust the size as needed
                .background(Color.Black, shape = RoundedCornerShape(8.dp))
        ) {
            // Load image from URL using Coil's rememberImagePainter
            Image(
                painter = rememberAsyncImagePainter(model = iconUrl),
                contentDescription = null,
               contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 16.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center)
            )
        }
    }


    @Composable
    fun Header() {
        Text(
            text = "Weather Forecast",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .padding(all = 20.dp)
        )
    }

    @Composable
    private fun subscribe() {
        val isLoading by mainViewModel.isLoading.observeAsState(false)
        val isError by mainViewModel.isError.observeAsState(false)
        val weatherData by mainViewModel.weatherData.observeAsState(null)
        var isPopupVisible by remember { mutableStateOf(false) }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .padding(16.dp)
            )
        }

        if (isError) {
            isPopupVisible = true
            AlertDialog(
                onDismissRequest = {
                    isPopupVisible = false
                },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text(text = mainViewModel.errorMessage ?: "An error occurred")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            isPopupVisible = false
                            Log.e("Error", mainViewModel.errorMessage ?: "An error occurred")
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }

        mainViewModel.errorMessage?.let { errorMessage ->
            if (errorMessage.contains("No matching location found", ignoreCase = true)) {
                // Show a specific message or UI for this error
                Text("No matching location found")
            }

        }

    }


    @Composable
    private fun setResultText(weatherData: CurrentWeatherResponse) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray) // Use the desired background color
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Display town name in a larger font
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("${weatherData.location?.name}")
                        }
                    },
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Display temperature
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 24.sp)) {
                            append("${weatherData.current?.tempC}°C")
                        }
                    },
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Additional weather information
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 18.sp)) {
                            append("Condition: ${weatherData.current?.condition?.text}\n")
                            append("Country: ${weatherData.location?.country}\n")
                            append("Timezone ID: ${weatherData.location?.tzId}\n")
                            append("Local Time: ${weatherData.location?.localtime}\n")
                            append("Wind speed (kph): ${weatherData.current?.wind_kph}\n")
                            append("Fahrenheit: ${weatherData.current?.tempF}\n")
                        }
                    },
                    color = Color.White
                )
            }
        }
    }
}



