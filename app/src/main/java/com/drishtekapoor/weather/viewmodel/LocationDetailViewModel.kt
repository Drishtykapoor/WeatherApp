package com.drishtekapoor.weather.viewmodel

import androidx.lifecycle.LiveData
import com.drishtekapoor.weather.repository.viewstate.LocationDetailViewState

interface LocationDetailViewModel{
    fun getData(cityName: String, state: String?, country: String?)
    fun getLocationLiveData(): LiveData<LocationDetailViewState>
}
