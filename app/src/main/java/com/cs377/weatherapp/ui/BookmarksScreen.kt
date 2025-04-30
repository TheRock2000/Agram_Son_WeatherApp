package com.cs377.weatherapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cs377.weatherapp.data.BookmarkedLocation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    viewModel: WeatherViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val bookmarkedLocations by viewModel.bookmarkedLocations.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bookmarked Locations") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (bookmarkedLocations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No bookmarked locations")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(bookmarkedLocations) { location ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        ListItem(
                            headlineContent = {
                                Text(location.name)
                            },
                            supportingContent = {
                                Text(location.country)
                            },
                            trailingContent = {
                                IconButton(
                                    onClick = { viewModel.removeBookmark(location) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Remove bookmark"
                                    )
                                }
                            },
                            modifier = Modifier.clickable {
                                viewModel.getWeatherByLocation(location.latitude, location.longitude)
                                onNavigateBack()
                            }
                        )
                    }
                }
            }
        }
    }
} 