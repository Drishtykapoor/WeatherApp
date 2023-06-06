package com.drishtekapoor.weather.di

import com.drishtekapoor.weather.view.SearchLocationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchLocationFragmentModule {

    @ContributesAndroidInjector(
        modules = [
            SearchLocationFragmentDependenciesModule::class
        ]
    )
    abstract fun provideSearchLocationFragment(): SearchLocationFragment
}