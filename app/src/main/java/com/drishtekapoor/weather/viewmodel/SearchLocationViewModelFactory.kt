package com.drishtekapoor.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drishtekapoor.weather.repository.SearchLocationRepository

class SearchLocationViewModelFactory (private val searchLocationRepository: SearchLocationRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchLocationViewModelImpl(searchLocationRepository) as T
    }

}