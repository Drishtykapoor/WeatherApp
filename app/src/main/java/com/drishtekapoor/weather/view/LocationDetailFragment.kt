package com.drishtekapoor.weather.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.drishtekapoor.weather.LocationActivity
import com.drishtekapoor.weather.MainActivity
import com.drishtekapoor.weather.R
import com.drishtekapoor.weather.databinding.LocationDetailFragmentBinding
import com.drishtekapoor.weather.repository.viewstate.LocationDetailViewState
import com.drishtekapoor.weather.viewmodel.LocationDetailViewModel
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LocationDetailFragment : DaggerFragment() {

    private lateinit var binding: LocationDetailFragmentBinding

    @Inject
    lateinit var viewModel: LocationDetailViewModel

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = LocationDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the title of the action bar
        (activity as MainActivity).supportActionBar?.title =
            resources.getText(R.string.weather_details)

        // Set a click listener on the updateLocation button to start LocationActivity
        binding.updateLocation.setOnClickListener {
            it.context.startActivity(Intent(it.context, LocationActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        // Retrieve the saved location details from SharedPreferences
        val locationName = sharedPreferences.getString("city", "")
        val state = sharedPreferences.getString("state", null)
        val country = sharedPreferences.getString("country", null)

        locationName?.let {
            // Observe the LiveData from the ViewModel to receive location details
            viewModel.getLocationLiveData().observe(viewLifecycleOwner) {
                when (it) {
                    // Handle empty state
                    LocationDetailViewState.Empty -> {
                        binding.progressBar.isVisible = false
                        binding.eventsErrorView.isVisible = true
                    }
                    is LocationDetailViewState.Error -> {
                        // Handle error state
                        binding.progressBar.isVisible = false
                        binding.eventsErrorView.isVisible = true
                    }
                    LocationDetailViewState.Loading -> {
                        // Show loading state
                        binding.progressBar.isVisible = true
                        binding.eventsErrorView.isVisible = false
                    }
                    is LocationDetailViewState.Success -> {
                        // Handle success state and populate UI with weather data
                        binding.progressBar.isVisible = false
                        binding.eventsErrorView.isVisible = false
                        val location = it.weatherData.name
                        val weather = it.weatherData.weather[0].description
                        val windSpeed = it.weatherData.wind.speed
                        val humidity = it.weatherData.main.humidity
                        val icon = it.weatherData.weather[0].icon
                        val iconUrl = "https://openweathermap.org/img/wn/$icon@2x.png"

                        // Load weather icon using Picasso library and display it in the ImageView
                        Picasso.with(context)
                            .load(iconUrl)
                            .into(binding.image)

                        // Set the weather information in the UI
                        binding.locationTitleDescription.text = location
                        binding.weatherDescription.text = weather
                        binding.windsSpeedDescription.text = windSpeed.toString()
                        binding.humidityDescription.text = humidity.toString()
                    }
                }
            }
        }
        // Request weather data for the location
        locationName?.let { viewModel.getData(it, state, country) }
    }


    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}