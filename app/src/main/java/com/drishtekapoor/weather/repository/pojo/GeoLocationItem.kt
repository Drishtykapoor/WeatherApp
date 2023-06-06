package com.drishtekapoor.weather.repository.pojo

data class GeoLocationItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)