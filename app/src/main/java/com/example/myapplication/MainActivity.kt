package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.runtime.livedata.observeAsState
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeweatherapp.core.designsystem.theme.ComposeWeatherAppTheme
import com.example.myapplication.model.CurrentWeatherResponse
import com.example.myapplication.viewmodel.MainViewModel

// https://medium.com/@dimaswisodewo98/fetch-data-from-api-in-android-studio-kotlin-using-retrofit-with-mvvm-architecture-4f6b673f6a28
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var etCityName: EditText
    private lateinit var imgCondition: ImageView
    private lateinit var tvResult: TextView
    private lateinit var btnSend: Button
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

            Spacer(modifier = Modifier.height(16.dp))

            // Weather result
            Text(
                text = "Weather Result",
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (cityName.isNotBlank()) {
                        mainViewModel.getWeatherData(cityName)
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

            Spacer(modifier = Modifier.height(40.dp))

            // Display Weather Information
            val weatherData by mainViewModel.weatherData.observeAsState(null)
            weatherData?.let { setResultText(it) }
        }
    }

    @Composable
    fun Header() {
        Text(
            text = "",
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
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
        // Display specific error message for the case where no matching location is found
        mainViewModel.errorMessage?.let { errorMessage ->
            if (errorMessage.contains("No matching location found", ignoreCase = true)) {
                // Show a specific message or UI for this error
                Text("No matching location found")
            }
        }
        weatherData?.let { setResultText(it) }
    }

    @Composable
    private fun setResultText(weatherData: CurrentWeatherResponse) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Load image from URL using NetworkImage
                weatherData.current?.condition?.icon?.let { iconUrl ->
                    Image(
                        painter = rememberCoilPainter(request = iconUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(16.dp))

                // Display text in a fancy way
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 18.sp)) {
                            append("Name: ${weatherData.location?.name}\n")

                            append("Country: ${weatherData.location?.country}\n")
                            append("Timezone ID: ${weatherData.location?.tzId}\n")
                            append("Local Time: ${weatherData.location?.localtime}\n")
                            append("\n")

                            append("Condition: ${weatherData.current?.condition?.text}\n")
                            append("Current temperature (C): ${weatherData.current?.tempC}\n")
                            append("Wind speed (kph): ${weatherData.current?.wind_kph}\n")
                            append("Fahrenheit: ${weatherData.current?.tempF}\n")
                        }
                    },
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.displayMedium,
                )
            }
        }
    }
}


