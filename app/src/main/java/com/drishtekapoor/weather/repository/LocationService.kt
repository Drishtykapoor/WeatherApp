package com.drishtekapoor.weather.repository

import com.drishtekapoor.weather.Constants
import com.drishtekapoor.weather.repository.pojo.GeoLocation
import com.drishtekapoor.weather.repository.pojo.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {

    @GET("data/2.5/weather")
    suspend fun getWeatherByLocation(
        @Query("q") values: String,
        @Query("appid") apiKey: String = Constants.API_KEY
    ): Response<WeatherData>

    @GET("/geo/1.0/reverse")
    suspend fun getWeatherByGeoCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("limit") values: String = "1" ,
        @Query("appid") apiKey: String = Constants.API_KEY
    ) : Response<GeoLocation>
}
