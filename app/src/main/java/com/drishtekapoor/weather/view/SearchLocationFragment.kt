package com.drishtekapoor.weather.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.drishtekapoor.weather.LocationActivity
import com.drishtekapoor.weather.MainActivity
import com.drishtekapoor.weather.R
import com.drishtekapoor.weather.databinding.SearchLocationFragmentBinding
import com.drishtekapoor.weather.repository.pojo.GeoLocationItem
import com.drishtekapoor.weather.repository.pojo.WeatherData
import com.drishtekapoor.weather.repository.viewstate.GeoLocationViewState
import com.drishtekapoor.weather.repository.viewstate.SearchLocationViewState
import com.drishtekapoor.weather.viewmodel.SearchLocationViewModel
import com.google.android.gms.location.LocationServices
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class SearchLocationFragment : DaggerFragment() {

    private lateinit var binding: SearchLocationFragmentBinding

    @Inject
    lateinit var viewModel: SearchLocationViewModel

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val locationPermissionRequestCode = 100


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchLocationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as LocationActivity).supportActionBar?.title =
            resources.getText(R.string.welcome_to_weatherApp)
        binding.search.setOnClickListener {
            viewModel.getData(binding.cityEditText.text.toString())
        }
        binding.requestPermissionButton.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getLocationLiveData().observe(viewLifecycleOwner) {
            when (it) {
                SearchLocationViewState.Empty -> showEmpty()
                SearchLocationViewState.Loading -> loading()
                is SearchLocationViewState.Success -> showSuccess(it.weatherData)
                is SearchLocationViewState.Error -> showError()
            }
        }

        viewModel.getGeoLocationLiveData().observe(viewLifecycleOwner) {
            when (it) {
                GeoLocationViewState.Empty -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(
                    context,
                    context?.resources?.getText(R.string.location_not_found),
                    Toast.LENGTH_LONG
                ).show()
                }
                GeoLocationViewState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is GeoLocationViewState.Success -> {
                    binding.progressBar.isVisible = false
                    setLocationData(it.geoLocationItem)
                }
                is GeoLocationViewState.Error -> {
                    binding.progressBar.isVisible = false
                }
            }
        }

    }

    private fun setLocationData(data: GeoLocationItem) {
        binding.cityEditText.setText(data.name)
        binding.countryEditText.setText(data.country)
        binding.stateEditText.setText(data.state)
    }

    private fun showSuccess(weatherData: WeatherData) {
        binding.progressBar.isVisible = false
        binding.cityEditText.setText(weatherData.name)
        sharedPreferences.edit()
            .putString("city", weatherData.name)
            .putString("state", binding.stateEditText.text.toString())
            .putString("country", binding.countryEditText.text.toString())
            .apply()
        context?.let {
            it.startActivity(Intent(it, MainActivity::class.java))
        }
        activity?.finish()
    }

    private fun loading() {
        binding.progressBar.isVisible = true
    }

    private fun showEmpty() {
        binding.progressBar.isVisible = false
        Toast.makeText(
            context,
            resources.getText(R.string.something_went_wrong),
            Toast.LENGTH_LONG
        ).show()

    }

    private fun showError() {
        binding.progressBar.isVisible = false
        Toast.makeText(context, "Please Enter Correct Info", Toast.LENGTH_LONG).show()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            // PERMISSION GRANTED
            context?.let { retrieveLocation(it) }
        } else {
            // PERMISSION NOT GRANTED
            Toast.makeText(context, "Permission Not Granted", Toast.LENGTH_LONG).show()

        }
    }

    @SuppressLint("MissingPermission")
    private fun retrieveLocation(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Use the retrieved location
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    viewModel.getLocation(latitude, longitude)
                } else {
                    // Location is null
                    // Handle the case when the location is not available
                    Toast.makeText(
                        context,
                        context.resources.getText(R.string.location_not_found),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            .addOnFailureListener { e: Exception ->
                Toast.makeText(
                    context,
                    context.resources.getText(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}