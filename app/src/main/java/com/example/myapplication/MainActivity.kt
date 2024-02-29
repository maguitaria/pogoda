package com.example.myapplication

import WeatherApp
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.ComposeWeatherAppTheme
// https://medium.com/@dimaswisodewo98/fetch-data-from-api-in-android-studio-kotlin-using-retrofit-with-mvvm-architecture-4f6b673f6a28


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ComposeWeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onSurface
                ) {
                    MainScreen()
                }

            }
        }

    }

@OptIn(ExperimentalMaterial3Api::class)
     @Preview
    @Composable
    fun MainScreen() {
        val navController = rememberNavController()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Jetpack Compose Navigation") },
                    navigationIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Menu")
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    HomeScreen(navController)
                }
                composable("newScreen") {
                    InfoScreen()
                }
            }
        }
    }


    @Composable
    fun HomeScreen(navController: NavHostController) {
        // Add your home screen UI components here
        Button(onClick = { navController.navigate("newScreen") }) {
            Text("Navigate to New Screen")
        }
        WeatherApp()
    }
    }



