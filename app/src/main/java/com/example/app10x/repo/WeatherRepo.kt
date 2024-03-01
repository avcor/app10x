package com.example.app10x.repo

import android.util.Log
import com.example.app10x.data.remote.ApiWeatherService
import com.example.app10x.data.remote.WeatherServiceResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

val TAG = "abcd"

class WeatherRepo @Inject constructor (
    private val apiWeatherService: ApiWeatherService
) {
    private val _responseStateFlow = MutableStateFlow<WeatherServiceResponse>(WeatherServiceResponse.Loading)
    val responseStateFlow: StateFlow<WeatherServiceResponse> = _responseStateFlow

    suspend fun getCurrentWeather() {
        _responseStateFlow.emit(WeatherServiceResponse.Loading)
        try {
            val r = apiWeatherService.getCurrentWeather(cityName = "Bengaluru")
            if(r.isSuccessful){
                r.body()?.let {
                    _responseStateFlow.emit(WeatherServiceResponse.Success(it))
                }?: run{
                    _responseStateFlow.emit(WeatherServiceResponse.NoData)
                    Log.d(TAG, "getCurrentWeather: ${r.body()}")
                }
            }else{
                _responseStateFlow.emit(WeatherServiceResponse.Failure)
                Log.d(TAG, "getCurrentWeather: fail")
            }
        }
        catch (e: Exception){
            _responseStateFlow.emit(WeatherServiceResponse.Failure)
            Log.e(TAG, "getCurrentWeather: Exception ${e}" )
        }
    }
}