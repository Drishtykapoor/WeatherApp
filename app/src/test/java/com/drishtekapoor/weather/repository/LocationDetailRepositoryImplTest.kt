package com.drishtekapoor.weather.repository

import com.drishtekapoor.weather.repository.pojo.*
import com.drishtekapoor.weather.repository.pojo.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
internal class LocationDetailRepositoryImplTest {

    @Mock
    lateinit var locationService: LocationService

    private lateinit var underTest: LocationDetailRepositoryImpl

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

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        // Create an instance of LocationDetailRepositoryImpl with the mocked locationService
        underTest = LocationDetailRepositoryImpl(locationService)
    }


    // Helper function to set the mock response for the locationService
    private suspend fun setMockResponse() {
        Mockito.`when`(locationService.getWeatherByLocation(weatherData.name)).thenReturn(
            Response.success(weatherData)
        )
    }

    @Test
    fun whenGetDataThenCallViewWithData() {
        runTest {
            val testDispatcher = UnconfinedTestDispatcher(testScheduler)

            // Set the main dispatcher and background dispatcher to the test dispatcher
            Dispatchers.setMain(testDispatcher)
            setMockResponse()

            // Call the getLocationData function of the repository
            val response = underTest.getLocationData(weatherData.name)
            
            // Assert that the response body matches the expected weatherData
            assertEquals(weatherData, response.body()!!)
        }
    }
}