package com.drishtekapoor.weather.di

import com.drishtekapoor.weather.LocationActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.drishtekapoor.weather.R
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationActivityModule {

    @ContributesAndroidInjector(
        modules = [
            UtilModule::class,
            NavigationModule::class,
            ApiModule::class,
            SearchLocationFragmentModule::class
        ]
    )
    abstract fun contributeLocationActivity(): LocationActivity

    @Module
    object NavigationModule {
        @Provides
        fun provideNavigationController(activity: LocationActivity): NavController {
            val navHostFragment =
                activity.supportFragmentManager
                    .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            return navHostFragment.navController
        }
    }
}