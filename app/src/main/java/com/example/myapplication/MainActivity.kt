package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.ExperimentalComposeUiApi
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
            TextField(
                value = cityName,
                onValueChange = { cityName = it },
                label = { Text(text = "Enter your city") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(bottom = 20.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 24.sp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                singleLine = true
            )
            // Weather result
            Text(
                text = "Weather Result",
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )

            Button(
                onClick = {
                    mainViewModel.getWeatherData(cityName)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Send Request")
            }
        }
    }

    @Composable
    fun Header() {
        Text(
            text = "Weather",
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

        if (isLoading) {
            // Handle loading state
        }

        if (isError) {
            // Handle error state
        }

        if (weatherData != null) {
            // Display weather data to the UI
            setResultText(weatherData)
        }
    }
@Composable
private fun setResultText(weatherData: CurrentWeatherResponse) {
    val resultText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 18.sp)) {
            append("Name: ${weatherData.location?.name}\n")
            append("Region: ${weatherData.location?.region}\n")
            append("Country: ${weatherData.location?.country}\n")
            append("Timezone ID: ${weatherData.location?.tzId}\n")
            append("Local Time: ${weatherData.location?.localtime}\n")
            append("\n")

            append("Condition: ${weatherData.current?.condition?.text}\n")
            append("Celcius: ${weatherData.current?.tempC}\n")
            append("Fahrenheit: ${weatherData.current?.tempF}\n")
        }
    }

    Text(
        text = resultText,
        modifier = Modifier.padding(bottom = 20.dp),
        fontSize = 18.sp
    )
    }
}

