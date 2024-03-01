package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodel.MainViewModel
import kotlinx.coroutines.launch



    @Composable
    fun subscribe(mainViewModel: MainViewModel) {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val isLoading by mainViewModel.isLoading.observeAsState(false)
        val isError by mainViewModel.isError.observeAsState(false)

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(70.dp)
                    .padding(16.dp)
            )
        }

        if (isError) {
            LaunchedEffect(isError) {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "An error occurred",
                        actionLabel = "Dismiss"
                    )
                }
            }
        }


        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(16.dp)
                .background(Color.White)

        ) { snackbarData ->
            Snackbar(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "An error occurred",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            snackbarHostState.currentSnackbarData?.dismiss()
                        }
                )
            }
        }
        }




