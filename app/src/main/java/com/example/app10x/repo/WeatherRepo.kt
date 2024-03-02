package com.example.app10x.repo

import android.util.Log
import com.example.app10x.data.remote.ApiWeatherService
import com.example.app10x.data.remote.model.City
import com.example.app10x.data.remote.type.WeatherServiceResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepo @Inject constructor(
    private val apiWeatherService: ApiWeatherService
) {
    suspend fun getCurrentWeather(city: String = "Bengaluru") = flow {
        try {
            val weatherResponse = apiWeatherService.getCurrentWeather(cityName = city)
            val forecastResponse = apiWeatherService.getForecastWeather(cityName = city)

            if ((weatherResponse.isSuccessful && weatherResponse.body() != null) &&
                (forecastResponse.isSuccessful && forecastResponse.body() != null)
            ) {
                emit(
                    WeatherServiceResponse.Success(
                        weatherResponse.body(),
                        forecastResponse.body()
                    )
                )
            } else {
                emit(WeatherServiceResponse.NoData)
            }
        } catch (e: Exception) {
            Log.e("WeatherRepo", "getCurrentWeather: exception $e")
            emit(WeatherServiceResponse.Failure)
        }
    }.flowOn(Dispatchers.IO)

}


