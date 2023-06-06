package com.drishtekapoor.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drishtekapoor.weather.repository.LocationDetailRepository

class LocationDetailViewModelFactory (private val locationDetailRepository: LocationDetailRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationDetailViewModelImpl(locationDetailRepository) as T
    }

}