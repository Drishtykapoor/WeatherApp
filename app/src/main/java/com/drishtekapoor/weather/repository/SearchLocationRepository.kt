package com.drishtekapoor.weather.repository

import com.drishtekapoor.weather.repository.pojo.GeoLocation
import com.drishtekapoor.weather.repository.pojo.GeoLocationItem
import com.drishtekapoor.weather.repository.pojo.WeatherData
import retrofit2.Response

interface SearchLocationRepository {
    suspend fun getData(values: String): Response<WeatherData>
    suspend fun getLocationData(lat: String, lon: String): Response<GeoLocation>
}
