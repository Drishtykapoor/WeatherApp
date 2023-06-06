package com.drishtekapoor.weather

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cityName = sharedPreferences.getString("city", null)
        if (cityName.isNullOrEmpty()) {
            startActivity(Intent(this, LocationActivity::class.java))
            finish()
        }
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}