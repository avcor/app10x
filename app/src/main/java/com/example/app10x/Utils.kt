package com.example.app10x

import android.util.Log
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val TAG = "utils"
fun Double.convertKelvinToCelsiusString(): String {
    return (this - 273.15).toString().substring(0, 4)
}

fun String.militaryToDateTime(): LocalDateTime? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    try {
        return LocalDate.parse(this, formatter).atTime(LocalTime.parse(this, formatter))
    } catch (e: Exception) {
        Log.e(TAG, "militaryToDate: Invalid date format $e")
    }
    return null
}

fun String.militaryToDate(): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    try {
        return LocalDate.parse(this, formatter)
    } catch (e: Exception) {
        Log.e(TAG, "militaryToDate: Invalid date format $e")
    }
    return null
}

fun String.dayToDateObj(): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    try {
        return LocalDate.parse(this, formatter)
    } catch (e: Exception) {
        Log.e(TAG,"Invalid date format day to Date Obj $e")
    }
    return null
}