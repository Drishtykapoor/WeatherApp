package com.drishtekapoor.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.drishtekapoor.weather.repository.LocationDetailRepository
import com.drishtekapoor.weather.repository.pojo.*
import com.drishtekapoor.weather.repository.viewstate.LocationDetailViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
internal class LocationDetailViewModelImplTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var locationDetailRepository: LocationDetailRepository

    private lateinit var underTest: LocationDetailViewModelImpl

    private val weatherData =
        WeatherData(
            wind = Wind(speed = 0.27, deg = 4),
            weather = listOf(
                Weather(
                    description = "overcast clouds",
                    icon = "",
                    id = 1,
                    main = "some-main-value"
                )
            ),
            main = Main(95.0, 1, 1, 1.0, 1.0, 1.0),
            name = "Zocca",
            coord = Coord(101.1, 202.2),
            dt = 100,
            id = 1,
            sys = Sys("usa", 1, 6, 9, 0),
            visibility = 20,
            timezone = 3,
            clouds = Clouds(10),
            cod = 5
        )

    // Expected success response based on the weather data
    private val expectedSuccessResponse = LocationDetailViewState.Success(weatherData)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun whenResponseIsSuccessFullSuccessStateIsSet() {
        runTest {
            val testDispatcher = UnconfinedTestDispatcher(testScheduler)

            // Set the main dispatcher and background dispatcher to the test dispatcher
            Dispatchers.setMain(testDispatcher)

            // Create an instance of LocationDetailViewModelImpl with mocked dependencies and test dispatcher
            underTest = LocationDetailViewModelImpl(
                locationDetailRepository,
                testDispatcher,
                testDispatcher
            )
            setMockSuccessResponse()

            // Create an observer to listen to changes in the locationLiveData
            val myObserver = MyObserver()
            underTest.getLocationLiveData().observeForever(myObserver)
            underTest.getData("Zocca", null, null)

            // Assert that the actual response received by the observer is the expected success response
            assertEquals(expectedSuccessResponse, myObserver.actualResponse)

            // Remove the observer to avoid memory leaks
            underTest.getLocationLiveData().removeObserver(myObserver)
        }
    }

    // Helper function to set the mock success response for the repository
    private suspend fun setMockSuccessResponse() {
        Mockito.`when`(locationDetailRepository.getLocationData(weatherData.name)).thenReturn(
            Response.success(weatherData)
        )
    }

    // Custom Observer implementation for testing
    class MyObserver : Observer<LocationDetailViewState> {
        var actualResponse: LocationDetailViewState? = null

        override fun onChanged(value: LocationDetailViewState) {
            actualResponse = value
        }
    }
}