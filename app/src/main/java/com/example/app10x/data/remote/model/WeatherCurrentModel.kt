package com.example.app10x.data.remote.model

import com.example.app10x.convertKelvinToCelsiusString

data class WeatherCurrentModel(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int,
){
    fun getCelsius(): String {
        return main.temp.convertKelvinToCelsiusString()
    }
}

