import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeweatherapp.core.designsystem.theme.ComposeWeatherAppTheme
import com.example.myapplication.R

class MainActivity : ComponentActivity() {
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
}

@Composable
fun WeatherApp() {
    var cityName by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        // City Name TextField
        OutlinedTextField(
            value = cityName,
            onValueChange = {
                cityName = it
            },
            label = {
                Text(text = stringResource(id = R.string.city_name))
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )

        // Condition Image
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with actual resource
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .padding(top = 20.dp, bottom = 20.dp)
        )

        // Result TextView
        Text(
            text = "Result", // Replace with actual result text
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )

        // Send Request Button
        Button(
            onClick = {
                // Handle button click
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(top = 20.dp)
        ) {
            Text(text = stringResource(id = R.string.send_request))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeatherApp() {
    WeatherApp()
}
