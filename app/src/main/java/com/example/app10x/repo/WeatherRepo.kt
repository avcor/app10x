package com.example.app10x.repo

import android.util.Log
import com.example.app10x.data.remote.ApiWeatherService
import com.example.app10x.data.remote.model.WeatherCurrentModel
import com.example.app10x.data.remote.model.WeatherForecastModel
import com.example.app10x.data.remote.type.WeatherServiceResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

val TAG = "abcd"

class WeatherRepo @Inject constructor(
    private val apiWeatherService: ApiWeatherService
) {
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(TAG, "Exception in Weather Repo $throwable")
        }

    suspend fun getCurrentWeather() = flow<WeatherServiceResponse> {
        emit(WeatherServiceResponse.Loading)

        try {
            val weatherResponse = apiWeatherService.getCurrentWeather(cityName = "Bengaluru")
            val forecastResponse = apiWeatherService.getForecastWeather(cityName = "Bengaluru")

            if ((weatherResponse.isSuccessful && weatherResponse.body() != null) && (forecastResponse.isSuccessful && forecastResponse.body() != null)){
                emit(WeatherServiceResponse.Success(weatherResponse.body(), forecastResponse.body()))
            }else{
                emit(WeatherServiceResponse.NoData)
            }
        }
        catch (e: Exception){
            emit(WeatherServiceResponse.Failure)
        }

    }.flowOn(Dispatchers.IO + coroutineExceptionHandler)

}