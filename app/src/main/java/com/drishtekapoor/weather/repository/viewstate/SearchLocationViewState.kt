package com.drishtekapoor.weather.repository.viewstate

import com.drishtekapoor.weather.repository.pojo.WeatherData

sealed class SearchLocationViewState {
        object Empty : SearchLocationViewState()
        object Loading : SearchLocationViewState()
        data class Success(val weatherData: WeatherData) : SearchLocationViewState()
        data class Error(val error: String?) : SearchLocationViewState()

}