package com.drishtekapoor.weather.repository.viewstate

import com.drishtekapoor.weather.repository.pojo.GeoLocationItem

sealed class GeoLocationViewState {
        object Empty : GeoLocationViewState()
        object Loading : GeoLocationViewState()
        data class Success(val geoLocationItem: GeoLocationItem) : GeoLocationViewState()
        data class Error(val error: String?) : GeoLocationViewState()

}