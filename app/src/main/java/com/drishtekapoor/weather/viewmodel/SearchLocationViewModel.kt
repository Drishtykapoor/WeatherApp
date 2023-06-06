package com.drishtekapoor.weather.viewmodel

import androidx.lifecycle.LiveData
import com.drishtekapoor.weather.repository.viewstate.GeoLocationViewState
import com.drishtekapoor.weather.repository.viewstate.SearchLocationViewState

interface SearchLocationViewModel{
    fun getData(cityName:String)
    fun getLocationLiveData(): LiveData<SearchLocationViewState>
    fun getLocation(lat: Double, lon: Double)
    fun getGeoLocationLiveData(): LiveData<GeoLocationViewState>
}
