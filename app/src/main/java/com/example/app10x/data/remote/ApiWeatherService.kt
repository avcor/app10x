package com.example.app10x.data.remote

import com.example.app10x.data.remote.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiWeatherService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") cityName : String,
        @Query("appid") apiKey: String = "e98ca12be2a8e26819977c3e3580026b"
    ): Response<WeatherResponse>

}
