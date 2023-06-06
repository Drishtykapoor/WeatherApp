package com.drishtekapoor.weather.di

import android.app.Application
import android.content.Context
import com.drishtekapoor.weather.MainApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector


@Component(
    modules = [
        MainActivityModule::class,
        LocationActivityModule::class,
        AndroidInjectionModule::class,
    ]
)
interface ApplicationComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun addContext(@BindsInstance context: Context): Builder
        fun build(): ApplicationComponent
    }

    override fun inject(mainApplication: MainApplication)

}