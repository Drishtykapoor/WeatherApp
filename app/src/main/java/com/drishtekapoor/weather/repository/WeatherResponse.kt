package com.drishtekapoor.weather.repository

import com.drishtekapoor.weather.repository.pojo.Main
import com.drishtekapoor.weather.repository.pojo.Weather
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("coord")
    val coordinates: Coordinates,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val city: String
)

data class Coordinates(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double
)

data class Weather(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

data class Main(
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("humidity")
    val humidity: Int
)
