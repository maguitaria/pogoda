package com.example.myapplication

import WeatherApp
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.ComposeWeatherAppTheme
import com.example.myapplication.viewmodel.MainViewModel

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

        var snackbarHostState: SnackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
        val navController = rememberNavController()




        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Jetpack Compose Navigation") },
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                    navigationIcon = {
                        Icon(
                            painter = painterResource(R.drawable.baseline_info_24),
                            contentDescription = stringResource(id = R.string.info_icon_desc),
                            modifier = Modifier.clickable {
                                navController.navigate("info")
                            }
                        )
                    },
                )
            },

        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    HomeScreen()
                }
                composable("info") {
                    InfoScreen(navController = navController )
                }

            }
        }


    }
    @Composable
    fun HomeScreen() {
       subscribe(mainViewModel = MainViewModel())
        WeatherApp()

    }
    }






