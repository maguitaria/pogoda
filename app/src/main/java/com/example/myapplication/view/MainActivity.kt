import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeweatherapp.core.designsystem.theme.ComposeWeatherAppTheme
import com.example.myapplication.R
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
                mainViewModel = MainViewModel()
                subscribe()
            }
        }

}

    @Preview
@Composable
fun WeatherApp() {
    var cityName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Weather",
            style = typography.displaySmall,
            modifier = Modifier
                .padding(all = 20.dp)
        )

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

private fun subscribe() {
    mainViewModel.isLoading.observe(this) { isLoading ->
        // Set the result text to Loading
        if (isLoading) tvResult.text = resources.getString(R.string.loading)
    }

    mainViewModel.isError.observe(this) { isError ->
        // Hide display image and set the result text to the error message
        if (isError) {
            imgCondition.visibility = View.GONE
            tvResult.text = mainViewModel.errorMessage
        }
    }

    mainViewModel.weatherData.observe(this) { weatherData ->
        // Display weather data to the UI
        setResultText(weatherData)
    }
}

private fun setResultText(weatherData: CurrentWeatherResponse) {
    val resultText = StringBuilder("Result:\n")

    weatherData.location.let { location ->
        resultText.append("Name: ${location?.name}\n")
        resultText.append("Region: ${location?.region}\n")
        resultText.append("Country: ${location?.country}\n")
        resultText.append("Timezone ID: ${location?.tzId}\n")
        resultText.append("Local Time: ${location?.localtime}\n")
    }

    weatherData.current.let { current ->
        current?.condition.let { condition ->
            resultText.append("Condition: ${condition?.text}\n")
        }
        resultText.append("Celcius: ${current?.tempC}\n")
        resultText.append("Fahrenheit: ${current?.tempF}\n")
    }

    tvResult.text = resultText.toString()
}

}

