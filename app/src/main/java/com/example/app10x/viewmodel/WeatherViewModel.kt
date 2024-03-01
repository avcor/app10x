package com.example.app10x.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app10x.data.remote.type.WeatherServiceResponse
import com.example.app10x.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo
): ViewModel() {

    private val _currentWeatherState = MutableStateFlow<WeatherServiceResponse>(
        WeatherServiceResponse.Loading)
    val currentWeatherState: StateFlow<WeatherServiceResponse> = _currentWeatherState
    init {
        viewModelScope.launch {
            weatherRepo.getCurrentWeather().collect{
                _currentWeatherState.value = it
            }
        }
    }
}