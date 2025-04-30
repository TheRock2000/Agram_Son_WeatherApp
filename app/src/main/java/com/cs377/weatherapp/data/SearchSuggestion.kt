package com.cs377.weatherapp.data

data class SearchSuggestion(
    val name: String,
    val country: String,
    val state: String? = null,
    val lat: Double,
    val lon: Double
) 