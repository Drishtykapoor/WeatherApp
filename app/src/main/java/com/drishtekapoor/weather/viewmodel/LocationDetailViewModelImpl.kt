package com.drishtekapoor.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drishtekapoor.weather.repository.LocationDetailRepository
import com.drishtekapoor.weather.repository.viewstate.LocationDetailViewState
import kotlinx.coroutines.*

class LocationDetailViewModelImpl(
    private val locationDetailRepository: LocationDetailRepository,
    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
) :
    ViewModel(), LocationDetailViewModel {

    private val locationWeatherData = MutableLiveData<LocationDetailViewState>()

    override fun getData(cityName: String, state: String?, country: String?) {

        locationWeatherData.value = LocationDetailViewState.Loading

        viewModelScope.launch(backgroundDispatcher) {
            val valueList = mutableListOf<String>()
            valueList.add(cityName)

            if (state != null) {
                valueList.add(state)
            }
            if (country != null) {
                valueList.add(country)
            }

            // Call the repository to fetch location weather data
            val response = locationDetailRepository.getLocationData(valueList.joinToString(","))
            withContext(mainDispatcher) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val data = response.body()!!.weather

                        // Check if weather data is empty
                        if (data.isEmpty()) {
                            locationWeatherData.value = LocationDetailViewState.Empty
                        } else {
                            locationWeatherData.value =
                                LocationDetailViewState.Success(response.body()!!)
                        }
                    } else locationWeatherData.value = LocationDetailViewState.Empty
                } else {
                    // Handle error response
                    locationWeatherData.value = LocationDetailViewState.Error(response.message())
                }
            }
        }
    }

    override fun getLocationLiveData(): LiveData<LocationDetailViewState> =
        locationWeatherData

}