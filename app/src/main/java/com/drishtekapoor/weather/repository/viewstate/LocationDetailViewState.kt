package com.drishtekapoor.weather.repository.viewstate

import com.drishtekapoor.weather.repository.pojo.WeatherData

sealed class LocationDetailViewState {
        object Empty : LocationDetailViewState()
        object Loading : LocationDetailViewState()
        data class Success(val weatherData: WeatherData) : LocationDetailViewState()
        data class Error(val error: String?) : LocationDetailViewState()

}