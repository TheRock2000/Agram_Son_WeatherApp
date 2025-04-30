package com.cs377.weatherapp.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "imperial",
        @Query("appid") apiKey: String = "62b2c06ca1e2aebb6d59556bad59a8c7"
    ): WeatherResponse

    @GET("weather")
    suspend fun getCurrentWeatherByCity(
        @Query("q") cityName: String,
        @Query("units") units: String = "imperial",
        @Query("appid") apiKey: String = "62b2c06ca1e2aebb6d59556bad59a8c7"
    ): WeatherResponse

    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "imperial",
        @Query("appid") apiKey: String = "62b2c06ca1e2aebb6d59556bad59a8c7"
    ): ForecastResponse

    @GET("forecast")
    suspend fun getForecastByCity(
        @Query("q") cityName: String,
        @Query("units") units: String = "imperial",
        @Query("appid") apiKey: String = "62b2c06ca1e2aebb6d59556bad59a8c7"
    ): ForecastResponse
}

interface GeoApiService {
    @GET("geo/1.0/direct")
    suspend fun searchLocations(
        @Query("q") query: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String = "62b2c06ca1e2aebb6d59556bad59a8c7"
    ): List<SearchSuggestionResponse>
}

object ApiClient {
    private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private const val GEO_BASE_URL = "https://api.openweathermap.org/"

    private val okHttpClient = OkHttpClient.Builder().build()

    fun createWeatherApiService(): WeatherApiService {
        return Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    fun createGeoApiService(): GeoApiService {
        return Retrofit.Builder()
            .baseUrl(GEO_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeoApiService::class.java)
    }
} 