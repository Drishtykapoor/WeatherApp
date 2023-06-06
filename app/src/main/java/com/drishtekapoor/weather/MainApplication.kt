package com.drishtekapoor.weather

import com.drishtekapoor.weather.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MainApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.builder().application(this).addContext(this).build()

    override fun onCreate() {
        super.onCreate()
        SharedPrefsManager.init(this)
    }
}