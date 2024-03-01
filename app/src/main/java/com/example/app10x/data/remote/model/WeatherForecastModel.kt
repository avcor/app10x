package com.example.app10x.data.remote.model

data class WeatherForecastModel(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val city: City
)

data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Long,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)
