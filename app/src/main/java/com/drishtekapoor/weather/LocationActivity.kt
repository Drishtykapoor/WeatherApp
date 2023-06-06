package com.drishtekapoor.weather

import android.os.Bundle
import com.drishtekapoor.weather.R
import dagger.android.support.DaggerAppCompatActivity

class LocationActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.location_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = resources?.getText(R.string.welcome_to_weatherApp)
    }

}
