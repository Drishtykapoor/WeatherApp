package com.drishtekapoor.weather.repository

import com.drishtekapoor.weather.Constants
import com.drishtekapoor.weather.repository.pojo.WeatherData
import retrofit2.Response
import javax.inject.Inject

class LocationDetailRepositoryImpl @Inject constructor(
    private val locationService: LocationService
) : LocationDetailRepository {

    override suspend fun getLocationData(values: String): Response<WeatherData> {
        return locationService.getWeatherByLocation(
            values,
            apiKey = Constants.API_KEY
        )
    }
}