package com.drishtekapoor.weather.repository

import com.drishtekapoor.weather.Constants
import com.drishtekapoor.weather.repository.pojo.GeoLocation
import retrofit2.Response
import javax.inject.Inject

class SearchLocationRepositoryImpl @Inject constructor(private val locationService: LocationService) :
    SearchLocationRepository {
    override suspend fun getData(values: String) =
        locationService.getWeatherByLocation(
            values,
            apiKey = Constants.API_KEY
        )

    override suspend fun getLocationData(lat: String, lon: String): Response<GeoLocation> =
        locationService.getWeatherByGeoCoordinates(lat, lon)
}