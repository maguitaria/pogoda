package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.CurrentWeatherResponse

class MainViewModel() : ViewModel() {
    private val _weatherData = MutableLiveData<CurrentWeatherResponse>()

}