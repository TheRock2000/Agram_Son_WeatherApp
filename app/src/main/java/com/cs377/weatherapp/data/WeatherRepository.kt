package com.cs377.weatherapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {
    private val weatherApiService = ApiClient.createWeatherApiService()
    private val geoApiService = ApiClient.createGeoApiService()
    
    suspend fun getWeatherByLocation(latitude: Double, longitude: Double): Result<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = weatherApiService.getCurrentWeather(latitude, longitude)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getWeatherByCity(cityName: String): Result<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = weatherApiService.getCurrentWeatherByCity(cityName)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getForecastByLocation(latitude: Double, longitude: Double): Result<ForecastResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = weatherApiService.getForecast(latitude, longitude)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getForecastByCity(cityName: String): Result<ForecastResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = weatherApiService.getForecastByCity(cityName)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun searchLocations(query: String): Result<List<SearchSuggestion>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = geoApiService.searchLocations(query)
                Result.success(response.map { location ->
                    SearchSuggestion(
                        name = location.name,
                        country = location.country,
                        state = location.state,
                        lat = location.lat,
                        lon = location.lon
                    )
                })
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
} 