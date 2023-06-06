package com.drishtekapoor.weather.di

import com.drishtekapoor.weather.view.LocationDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationDetailFragmentModule {

    @ContributesAndroidInjector(
        modules = [
            LocationDetailFragmentDependenciesModule::class
        ]
    )
    abstract fun provideLocationDetailFragment(): LocationDetailFragment

}