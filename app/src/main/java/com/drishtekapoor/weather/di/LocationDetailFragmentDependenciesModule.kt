package com.drishtekapoor.weather.di

import androidx.lifecycle.ViewModelProvider
import com.drishtekapoor.weather.repository.LocationDetailRepository
import com.drishtekapoor.weather.repository.LocationDetailRepositoryImpl
import com.drishtekapoor.weather.repository.LocationService
import com.drishtekapoor.weather.view.LocationDetailFragment
import com.drishtekapoor.weather.viewmodel.LocationDetailViewModel
import com.drishtekapoor.weather.viewmodel.LocationDetailViewModelFactory
import com.drishtekapoor.weather.viewmodel.LocationDetailViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [LocationDetailFragmentDependenciesModule.LocationDetailProvidesModule::class])
interface LocationDetailFragmentDependenciesModule {

    @Binds
    fun provideLocationDetailRepository(locationDetailRepositoryImpl: LocationDetailRepositoryImpl): LocationDetailRepository

    @Binds
    fun provideLocationDetailViewModel(locationDetailViewModelImpl: LocationDetailViewModelImpl): LocationDetailViewModel

    @Module
    object LocationDetailProvidesModule {

        @Provides
        fun provideLocationDetailViewModelFactory(locationDetailRepository: LocationDetailRepository): LocationDetailViewModelFactory {
            return LocationDetailViewModelFactory(locationDetailRepository)
        }

        @Provides
        fun provideViewModel(
            locationDetailFragment: LocationDetailFragment,
            locationDetailViewModelFactory: LocationDetailViewModelFactory
        ): LocationDetailViewModelImpl {
            val v = ViewModelProvider(
                locationDetailFragment, locationDetailViewModelFactory
            )
            return v[LocationDetailViewModelImpl::class.java]
        }

        @Provides
        fun provideRepository(
            locationService: LocationService
        ): LocationDetailRepositoryImpl {
            return LocationDetailRepositoryImpl(locationService)
        }
    }

}

