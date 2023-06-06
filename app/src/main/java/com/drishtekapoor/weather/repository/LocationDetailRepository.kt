package com.drishtekapoor.weather.repository

import com.drishtekapoor.weather.repository.pojo.WeatherData
import retrofit2.Response

interface LocationDetailRepository {
    suspend fun getLocationData(values: String): Response<WeatherData>
}
