package com.example.app10x.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app10x.data.remote.type.WeatherServiceResponse
import com.example.app10x.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo
) : ViewModel() {

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.e("WeatherViewModel", "Exception in Weather Repo $throwable")
        }

    private val _currentWeatherState = MutableStateFlow<WeatherServiceResponse>(WeatherServiceResponse.Loading)
    val currentWeatherState: StateFlow<WeatherServiceResponse> = _currentWeatherState

    private var _city = "Bengaluru"
    val city = _city

    init {
        getWeatherData()
    }

    fun getWeatherData() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            weatherRepo.getCurrentWeather(city = city)
                .collect {
                    _currentWeatherState.value = it
                }
        }
    }

    fun setCity(newCity: String) {
        _city = newCity
    }

}