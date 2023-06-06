package com.drishtekapoor.weather.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class UtilModule {

    @Provides
    fun provideSharePrefs(context: Context): SharedPreferences =
        context.getSharedPreferences("main", Context.MODE_PRIVATE)

}