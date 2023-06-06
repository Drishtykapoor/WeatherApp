package com.drishtekapoor.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drishtekapoor.weather.repository.SearchLocationRepository
import com.drishtekapoor.weather.repository.viewstate.GeoLocationViewState
import com.drishtekapoor.weather.repository.viewstate.SearchLocationViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchLocationViewModelImpl(private val searchLocationRepository: SearchLocationRepository) :
    ViewModel(), SearchLocationViewModel {

    private val locationData = MutableLiveData<SearchLocationViewState>()

    private val geoLocationData= MutableLiveData<GeoLocationViewState>()

    override fun getData(cityName: String) {
        // Set the loading state of locationData
        locationData.value = SearchLocationViewState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            // Call the repository to fetch location data
            val response =
                searchLocationRepository.getData(cityName)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val data = response.body()!!.weather

                        // Check if the weather data is empty
                        if (data.isEmpty()) {
                            locationData.value = SearchLocationViewState.Empty
                        } else {
                            locationData.value = SearchLocationViewState.Success(response.body()!!)
                        }
                    } else locationData.value = SearchLocationViewState.Empty
                } else {
                    locationData.value = SearchLocationViewState.Error(response.message())
                }
            }
        }
    }

    override fun getLocationLiveData(): LiveData<SearchLocationViewState> = locationData

    override fun getLocation(lat: Double, lon: Double) {
        // Set the loading state of geoLocationData
        geoLocationData.value = GeoLocationViewState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            // Call the repository to fetch geo location data
            val response =
                searchLocationRepository.getLocationData(lat.toString(), lon.toString())
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val data = response.body()!!
                        if (data.isEmpty()) {
                            geoLocationData.value = GeoLocationViewState.Empty
                        } else {
                            geoLocationData.value = GeoLocationViewState.Success(data[0])
                        }
                    } else geoLocationData.value = GeoLocationViewState.Empty
                } else {
                    geoLocationData.value = GeoLocationViewState.Error(response.message())
                }
            }
        }
    }
    override fun getGeoLocationLiveData(): LiveData<GeoLocationViewState> = geoLocationData


}