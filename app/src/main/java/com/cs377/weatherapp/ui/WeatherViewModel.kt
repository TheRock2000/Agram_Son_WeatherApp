package com.cs377.weatherapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs377.weatherapp.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

sealed class WeatherUiState {
    data object Loading : WeatherUiState()
    data class Success(val weatherData: WeatherResponse, val forecastData: ForecastResponse? = null) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
    data object Empty : WeatherUiState()
}

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()
    private lateinit var locationDao: LocationDao
    
    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _locationName = MutableStateFlow("Your Location")
    val locationName: StateFlow<String> = _locationName.asStateFlow()
    
    private val _bookmarkedLocations = MutableStateFlow<List<BookmarkedLocation>>(emptyList())
    val bookmarkedLocations: StateFlow<List<BookmarkedLocation>> = _bookmarkedLocations.asStateFlow()
    
    private val _searchSuggestions = MutableStateFlow<List<SearchSuggestion>>(emptyList())
    val searchSuggestions: StateFlow<List<SearchSuggestion>> = _searchSuggestions.asStateFlow()
    
    private val apiKey = "62b2c06ca1e2aebb6d59556bad59a8c7"
    
    fun initializeDatabase(context: Context) {
        locationDao = WeatherDatabase.getDatabase(context).locationDao()
        viewModelScope.launch {
            locationDao.getAllLocations().collect { locations ->
                _bookmarkedLocations.value = locations
            }
        }
    }
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun searchCity(city: String) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            try {
                val weatherResult = repository.getWeatherByCity(city)
                weatherResult.onSuccess { weatherResponse ->
                    val forecastResult = repository.getForecastByCity(city)
                    forecastResult.onSuccess { forecastResponse ->
                        _locationName.value = weatherResponse.name
                        _uiState.value = WeatherUiState.Success(weatherResponse, forecastResponse)
                    }.onFailure { error ->
                        // If forecast fails but weather succeeds, still show weather
                        _locationName.value = weatherResponse.name
                        _uiState.value = WeatherUiState.Success(weatherResponse)
                    }
                }.onFailure { error ->
                    _uiState.value = WeatherUiState.Error(error.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    fun getWeatherByLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            try {
                val weatherResult = repository.getWeatherByLocation(latitude, longitude)
                weatherResult.onSuccess { weatherResponse ->
                    val forecastResult = repository.getForecastByLocation(latitude, longitude)
                    forecastResult.onSuccess { forecastResponse ->
                        _locationName.value = weatherResponse.name
                        _uiState.value = WeatherUiState.Success(weatherResponse, forecastResponse)
                    }.onFailure { error ->
                        // If forecast fails but weather succeeds, still show weather
                        _locationName.value = weatherResponse.name
                        _uiState.value = WeatherUiState.Success(weatherResponse)
                    }
                }.onFailure { error ->
                    _uiState.value = WeatherUiState.Error(error.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    fun addBookmark(location: BookmarkedLocation) {
        viewModelScope.launch {
            locationDao.insertLocation(location)
        }
    }
    
    fun removeBookmark(location: BookmarkedLocation) {
        viewModelScope.launch {
            locationDao.deleteLocation(location)
        }
    }
    
    fun isLocationBookmarked(name: String): Boolean {
        return _bookmarkedLocations.value.any { it.name == name }
    }
    
    fun fetchSearchSuggestions(query: String) {
        if (query.length < 2) {
            _searchSuggestions.value = emptyList()
            return
        }
        
        viewModelScope.launch {
            try {
                val result = repository.searchLocations(query)
                result.onSuccess { suggestions ->
                    _searchSuggestions.value = suggestions.map { suggestion ->
                        SearchSuggestion(
                            name = suggestion.name,
                            country = suggestion.country,
                            state = suggestion.state,
                            lat = suggestion.lat,
                            lon = suggestion.lon
                        )
                    }
                }.onFailure {
                    _searchSuggestions.value = emptyList()
                }
            } catch (e: Exception) {
                _searchSuggestions.value = emptyList()
            }
        }
    }
    
    fun clearSuggestions() {
        _searchSuggestions.value = emptyList()
    }
}

data class SearchSuggestionResponse(
    val name: String,
    val country: String,
    val state: String? = null,
    val lat: Double,
    val lon: Double
) 