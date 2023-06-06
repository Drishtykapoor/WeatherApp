package com.drishtekapoor.weather.repository.pojo

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)