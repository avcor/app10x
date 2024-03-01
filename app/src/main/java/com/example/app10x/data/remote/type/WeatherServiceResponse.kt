package com.example.app10x.data.remote.type

import com.example.app10x.data.remote.model.WeatherForecastModel
import com.example.app10x.data.remote.model.WeatherCurrentModel

sealed interface WeatherServiceResponse {
    data class Success(
        val currentWeather: WeatherCurrentModel?,
        val forecastWeather: WeatherForecastModel?
    ) : WeatherServiceResponse
    data object Failure : WeatherServiceResponse
    data object Loading : WeatherServiceResponse
    data object NoData : WeatherServiceResponse
}