import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.model.CurrentWeatherResponse
import com.example.myapplication.subscribe
import com.example.myapplication.viewmodel.MainViewModel
import kotlinx.coroutines.launch


@Composable
fun WeatherApp(mainViewModel: MainViewModel = viewModel()) {

    var cityName by remember { mutableStateOf("") }
    var cityNameInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Header()
        Spacer(modifier = Modifier.height(16.dp))


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

        Button(
            onClick = {
                if (cityName.isNotBlank()) {
                    mainViewModel.getWeatherData(cityName)
                    cityNameInput = cityName

                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp),
            enabled = cityName.isNotBlank()
        ) {
            Icon(Icons.Default.Send, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Send Request")
        }

        Spacer(modifier = Modifier.height(16.dp))
        subscribe(mainViewModel)
        val weatherData by mainViewModel.weatherData.observeAsState(null)
        weatherData?.let {
            CoilImage(it)
            Spacer(modifier = Modifier.height(16.dp))
            setResultText(it)
        }
    }
}


@Composable
private fun CoilImage(weatherData: CurrentWeatherResponse) {

    val iconUrl = "https:${weatherData.current?.condition?.icon}"
    println(iconUrl)

    Box(
        modifier = Modifier
            .size(120.dp)
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Load image from URL using Coil's rememberImagePainter
        Image(
            painter = rememberAsyncImagePainter(model = iconUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 5.dp)
                .clip(CircleShape)
                .align(Alignment.Center)
        )
    }
}


@Composable
fun Header() {
    Text(
        text = "Weather Forecast",
        style = MaterialTheme.typography.displayMedium,
        color  = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(all = 5.dp)
    )
}

@Composable
fun setResultText(weatherData: CurrentWeatherResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground) // Use the desired background color
            .padding(16.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary),
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
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 8.dp)

            )

            // Display temperature
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 24.sp)) {
                        append("${weatherData.current?.tempC}°C")
                    }
                },
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Display temperature
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 24.sp)) {
                        append("${weatherData.current?.condition?.text}\n")
                        append(" ${weatherData.location?.country}")
                    }
                },
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append("Timezone ID: ${weatherData.location?.tzId}\n")
                        append("Local Time: ${weatherData.location?.localtime}\n")
                        append("Wind speed (kph): ${weatherData.current?.wind_kph}\n")
                        append("Fahrenheit: ${weatherData.current?.tempF}\n")
                    }
                },
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}