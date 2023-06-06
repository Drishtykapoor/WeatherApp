package com.drishtekapoor.weather.di

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.drishtekapoor.weather.MainActivity
import com.drishtekapoor.weather.R
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(
        modules = [
            UtilModule::class,
            NavigationModule::class,
            ApiModule::class,
            LocationDetailFragmentModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity

    @Module
    object NavigationModule {
        @Provides
        fun provideNavigationController(mainActivity: MainActivity): NavController {
            val navHostFragment =
                mainActivity.supportFragmentManager
                    .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            return navHostFragment.navController
        }
    }
}