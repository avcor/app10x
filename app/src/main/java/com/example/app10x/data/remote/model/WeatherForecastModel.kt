package com.example.app10x.data.remote.model

import android.util.Log
import com.example.app10x.dayToDateObj
import com.example.app10x.militaryToDate
import com.example.app10x.militaryToDateTime
import com.example.app10x.ui.mainactivity.TAG

data class WeatherForecastModel(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<WeatherData>,
    val city: City
){
    fun get4DayAverage(): List<ForecastPair> {
        val hash = mutableMapOf<String, Pair<Double, Int >>()
        val res = mutableListOf<ForecastPair>()

        for(l in list){
            val key = l.dt_txt.militaryToDate()?.let{
                it.toString()
            } ?: continue

            hash[key]?.let {
                hash[key] = Pair(it.first + l.main.temp, it.second+1)
            } ?: run {
                hash[key] = Pair(l.main.temp, 1)
            }
        }
        Log.d(TAG, "get4DayAverage: " + hash)

        for(h in hash){
            val v = ForecastPair(h.key.dayToDateObj(), (h.value.first/h.value.second))
            res.add(v)
        }
        return res
    }
}


data class WeatherData(
    val dt: Long,
    val main: MainData,
    val weather: List<WeatherDetail>,
    val clouds: CloudData,
    val wind: WindData,
    val visibility: Int,
    val sys: SysData,
    val dt_txt: String
)


data class MainData(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val sea_level: Int,
    val grnd_level: Int,
    val humidity: Int,
    val temp_kf: Double
)

data class WeatherDetail(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class CloudData(
    val all: Int
)

data class WindData(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

data class SysData(
    val pod: String
)

