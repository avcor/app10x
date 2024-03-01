package com.example.app10x.data.remote

import com.example.app10x.data.remote.model.WeatherResponse

sealed interface WeatherServiceResponse {
    data class Success(val responseData: WeatherResponse) : WeatherServiceResponse
    data object Failure: WeatherServiceResponse
    data object Loading: WeatherServiceResponse
    data object NoData: WeatherServiceResponse
}