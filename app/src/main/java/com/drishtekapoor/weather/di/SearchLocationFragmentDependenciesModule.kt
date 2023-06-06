package com.drishtekapoor.weather.di

import androidx.lifecycle.ViewModelProvider
import com.drishtekapoor.weather.repository.LocationService
import com.drishtekapoor.weather.repository.SearchLocationRepository
import com.drishtekapoor.weather.repository.SearchLocationRepositoryImpl
import com.drishtekapoor.weather.view.SearchLocationFragment
import com.drishtekapoor.weather.viewmodel.SearchLocationViewModel
import com.drishtekapoor.weather.viewmodel.SearchLocationViewModelFactory
import com.drishtekapoor.weather.viewmodel.SearchLocationViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [SearchLocationFragmentDependenciesModule.SearchFragmentProvidesModule::class])
interface SearchLocationFragmentDependenciesModule {

    @Binds
    fun bindRepository(repositoryImpl: SearchLocationRepositoryImpl): SearchLocationRepository

    @Binds
    fun bindViewModel(viewModelImpl: SearchLocationViewModelImpl): SearchLocationViewModel

    @Module
    object SearchFragmentProvidesModule {

        @Provides
        fun provideViewModelFactory(repository: SearchLocationRepository): SearchLocationViewModelFactory {
            return SearchLocationViewModelFactory(repository)
        }

        @Provides
        fun provideViewModel(
            fragment: SearchLocationFragment,
            viewModelFactory: SearchLocationViewModelFactory
        ): SearchLocationViewModelImpl {
            val v = ViewModelProvider(
                fragment,
                viewModelFactory
            )
            return v[SearchLocationViewModelImpl::class.java]
        }

        @Provides
        fun provideRepository(
            service: LocationService
        ): SearchLocationRepositoryImpl {
            return SearchLocationRepositoryImpl(service)
        }
    }

}

