// InfoScreen.kt

package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.ComposeWeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface)
    ) {

        InfoScreenAppBar(navController = navController)

        Column(
            modifier = Modifier
                .padding(25.dp)
                .background(MaterialTheme.colorScheme.onSurface)
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Final Project for Mobile Programming with Kotlin",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Utilizes OpenWeather API for weather data.",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Give Feedback:",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreenAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("Project Info") },
        modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = stringResource(id = R.string.back_icon_desc),
                modifier = Modifier.clickable {
                    navController.navigateUp()
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun InfoScreenPreview() {
    ComposeWeatherAppTheme {
        InfoScreen(navController = rememberNavController())
    }
}