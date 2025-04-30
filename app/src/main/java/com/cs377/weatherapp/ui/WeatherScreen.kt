package com.cs377.weatherapp.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cs377.weatherapp.data.BookmarkedLocation
import com.cs377.weatherapp.data.SearchSuggestion
import com.cs377.weatherapp.data.Weather
import com.cs377.weatherapp.data.WeatherResponse
import com.cs377.weatherapp.data.ForecastResponse
import com.cs377.weatherapp.data.ForecastItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.material.ripple.rememberRipple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = viewModel(),
    onNavigateToBookmarks: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val locationName by viewModel.locationName.collectAsState()
    val bookmarkedLocations by viewModel.bookmarkedLocations.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var locationPermissionGranted by remember { mutableStateOf(false) }
    var selectedForecast by remember { mutableStateOf<ForecastItem?>(null) }
    val locationManager = rememberLocationManager()
    val context = LocalContext.current
    
    LaunchedEffect(Unit) {
        viewModel.initializeDatabase(context)
    }
    
    // Get weather-based colors
    val weatherColors = when {
        uiState is WeatherUiState.Success -> {
            val weather = (uiState as WeatherUiState.Success).weatherData.weather.firstOrNull()?.main?.lowercase() ?: ""
            when (weather) {
                "clear" -> listOf(Color(0xFF87CEEB), Color(0xFF1E90FF))  // Sky blue gradient
                "clouds" -> listOf(Color(0xFF778899), Color(0xFF4682B4))  // Cloudy blue-gray
                "rain" -> listOf(Color(0xFF4682B4), Color(0xFF000080))    // Dark blue for rain
                "snow" -> listOf(Color(0xFFB0E0E6), Color(0xFF87CEEB))    // Light blue for snow
                "thunderstorm" -> listOf(Color(0xFF483D8B), Color(0xFF000080))  // Deep purple for storms
                else -> listOf(Color(0xFF87CEEB), Color(0xFF1E90FF))      // Default sky blue
            }
        }
        else -> listOf(Color(0xFF87CEEB), Color(0xFF1E90FF))  // Default sky blue
    }
    
    LocationPermissionHandler(
        onPermissionGranted = {
            locationPermissionGranted = true
            if (uiState is WeatherUiState.Empty) {
                locationManager.getCurrentLocation(
                    onSuccess = { location ->
                        viewModel.getWeatherByLocation(location.latitude, location.longitude)
                    },
                    onFailure = { error ->
                        // Handle location error
                    }
                )
            }
        },
        onPermissionDenied = {
            locationPermissionGranted = false
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Dynamic gradient background based on weather
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = weatherColors,
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            // Semi-transparent overlay for better readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.15f))
            )

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Top Bar with animation
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = onNavigateToBookmarks,
                                modifier = Modifier
                                    .shadow(4.dp, RoundedCornerShape(12.dp))
                                    .background(
                                        MaterialTheme.colorScheme.surface,
                                        RoundedCornerShape(12.dp)
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Bookmarks Menu",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    // Location Header with animation
                    if (uiState is WeatherUiState.Success) {
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut() + slideOutVertically()
                        ) {
                            Text(
                                text = locationName,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }

                    // Search Bar with animation
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { 
                                searchQuery = it
                                if (it.length >= 2) {
                                    viewModel.fetchSearchSuggestions(it)
                                } else {
                                    viewModel.clearSuggestions()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .shadow(4.dp, RoundedCornerShape(16.dp)),
                            placeholder = { Text("Search for a city") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            trailingIcon = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    if (uiState is WeatherUiState.Success) {
                                        val weatherData = (uiState as WeatherUiState.Success).weatherData
                                        val interactionSource = remember { MutableInteractionSource() }
                                        val isPressed by interactionSource.collectIsPressedAsState()
                                        val isBookmarked = viewModel.isLocationBookmarked(weatherData.name)
                                        
                                        // Animation values
                                        val scale by animateFloatAsState(
                                            targetValue = if (isPressed) 0.8f else 1f,
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow
                                            )
                                        )
                                        
                                        val rotation by animateFloatAsState(
                                            targetValue = if (isBookmarked) 360f else 0f,
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow
                                            )
                                        )

                                        IconButton(
                                            onClick = {
                                                if (isBookmarked) {
                                                    viewModel.removeBookmark(
                                                        BookmarkedLocation(
                                                            name = weatherData.name,
                                                            latitude = weatherData.coord.lat,
                                                            longitude = weatherData.coord.lon,
                                                            country = weatherData.sys.country
                                                        )
                                                    )
                                                } else {
                                                    viewModel.addBookmark(
                                                        BookmarkedLocation(
                                                            name = weatherData.name,
                                                            latitude = weatherData.coord.lat,
                                                            longitude = weatherData.coord.lon,
                                                            country = weatherData.sys.country
                                                        )
                                                    )
                                                }
                                            },
                                            interactionSource = interactionSource,
                                            modifier = Modifier.graphicsLayer {
                                                scaleX = scale
                                                scaleY = scale
                                                rotationZ = rotation
                                            }
                                        ) {
                                            Icon(
                                                imageVector = if (isBookmarked) {
                                                    Icons.Default.Bookmark
                                                } else {
                                                    Icons.Default.BookmarkBorder
                                                },
                                                contentDescription = if (isBookmarked) {
                                                    "Remove from bookmarks"
                                                } else {
                                                    "Add to bookmarks"
                                                },
                                                tint = if (isBookmarked) {
                                                    MaterialTheme.colorScheme.primary
                                                } else {
                                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                                                }
                                            )
                                        }
                                    }
                                    if (searchQuery.isNotEmpty()) {
                                        IconButton(onClick = { 
                                            searchQuery = ""
                                            viewModel.clearSuggestions()
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "Clear",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    if (searchQuery.isNotEmpty()) {
                                        viewModel.searchCity(searchQuery)
                                        viewModel.clearSuggestions()
                                    }
                                }
                            ),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                                unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                                focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                                unfocusedTrailingIconColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    // Search Suggestions with animation
                    val suggestions by viewModel.searchSuggestions.collectAsState()
                    AnimatedVisibility(
                        visible = suggestions.isNotEmpty(),
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column {
                                suggestions.forEach { suggestion ->
                                    SuggestionItem(
                                        suggestion = suggestion,
                                        onClick = {
                                            searchQuery = "${suggestion.name}, ${suggestion.country}"
                                            viewModel.getWeatherByLocation(suggestion.lat, suggestion.lon)
                                            viewModel.clearSuggestions()
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Weather Content with animation
                    AnimatedVisibility(
                        visible = uiState is WeatherUiState.Success,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        when (uiState) {
                            is WeatherUiState.Empty -> {
                                EmptyState()
                            }
                            is WeatherUiState.Loading -> {
                                LoadingState()
                            }
                            is WeatherUiState.Success -> {
                                val weatherData = (uiState as WeatherUiState.Success).weatherData
                                val forecastData = (uiState as WeatherUiState.Success).forecastData

                                Column {
                                    // Show either selected forecast or current weather
                                    if (selectedForecast != null) {
                                        WeatherContent(
                                            weatherData = WeatherResponse(
                                                name = weatherData.name,
                                                coord = weatherData.coord,
                                                main = selectedForecast!!.main,
                                                weather = selectedForecast!!.weather,
                                                wind = selectedForecast!!.wind,
                                                sys = weatherData.sys,
                                                dt = selectedForecast!!.dt,
                                                base = weatherData.base,
                                                visibility = weatherData.visibility,
                                                clouds = weatherData.clouds,
                                                timezone = weatherData.timezone,
                                                id = weatherData.id,
                                                cod = weatherData.cod
                                            ),
                                            forecastData = null,
                                            onBackClick = { selectedForecast = null }
                                        )
                                    } else {
                                        WeatherContent(
                                            weatherData = weatherData,
                                            forecastData = forecastData,
                                            onBackClick = null
                                        )
                                    }

                                    // 5-Day Forecast Section
                                    if (forecastData?.list != null && selectedForecast == null) {
                                        val dailyForecasts = forecastData.list
                                            .groupBy { getDateOnly(it.dt) }
                                            .map { (_, items) -> items.first() }
                                            .take(5)

                                        if (dailyForecasts.isNotEmpty()) {
                                            Spacer(modifier = Modifier.height(24.dp))
                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 8.dp),
                                                colors = CardDefaults.cardColors(
                                                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                                                ),
                                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                                shape = RoundedCornerShape(16.dp)
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(16.dp)
                                                ) {
                                                    Text(
                                                        text = "5-Day Forecast",
                                                        style = MaterialTheme.typography.titleLarge,
                                                        color = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.padding(bottom = 16.dp),
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                    ForecastList(
                                                        forecastData = dailyForecasts,
                                                        onForecastClick = { selectedForecast = it }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            is WeatherUiState.Error -> {
                                val errorMessage = (uiState as WeatherUiState.Error).message
                                ErrorState(message = errorMessage)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WeatherContent(
    weatherData: WeatherResponse,
    forecastData: ForecastResponse?,
    onBackClick: (() -> Unit)?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back button for forecast view
        if (onBackClick != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to current weather",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Location and Date with enhanced styling
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${weatherData.name}, ${weatherData.sys.country}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                
                Text(
                    text = getFormattedDate(weatherData.dt),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Temperature with enhanced animation
                val temperature by animateFloatAsState(
                    targetValue = weatherData.main.temp.roundToInt().toFloat(),
                    animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                )
                
                Text(
                    text = "${temperature.roundToInt()}°F",
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                // Weather description with icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    WeatherIcon(
                        weather = weatherData.weather.firstOrNull(),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = weatherData.weather.firstOrNull()?.description?.capitalize() ?: "",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Weather details with enhanced styling
        WeatherDetails(weatherData)
    }
}

@Composable
private fun WeatherDetails(weatherData: WeatherResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Weather Details",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.Bold
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailItem(
                    label = "Feels Like",
                    value = "${weatherData.main.feels_like.roundToInt()}°F",
                    icon = Icons.Default.LocationOn
                )
                DetailItem(
                    label = "Humidity",
                    value = "${weatherData.main.humidity}%",
                    icon = Icons.Default.LocationOn
                )
                DetailItem(
                    label = "Wind",
                    value = "${weatherData.wind.speed} m/s",
                    icon = Icons.Default.LocationOn
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailItem(
                    label = "Min Temp",
                    value = "${weatherData.main.temp_min.roundToInt()}°F",
                    icon = Icons.Default.LocationOn
                )
                DetailItem(
                    label = "Max Temp",
                    value = "${weatherData.main.temp_max.roundToInt()}°F",
                    icon = Icons.Default.LocationOn
                )
                DetailItem(
                    label = "Pressure",
                    value = "${weatherData.main.pressure} hPa",
                    icon = Icons.Default.LocationOn
                )
            }
        }
    }
}

@Composable
private fun DetailItem(label: String, value: String, icon: ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun SuggestionItem(
    suggestion: SearchSuggestion,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = {
            Column {
                Text(
                    text = suggestion.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${suggestion.state?.let { "$it, " } ?: ""}${suggestion.country}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    )
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Search for a city to see the weather",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(48.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Error: $message",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun ForecastList(
    forecastData: List<ForecastItem>,
    onForecastClick: (ForecastItem) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        forecastData.forEachIndexed { index, forecast ->
            ForecastItem(
                forecast = forecast,
                onClick = { onForecastClick(forecast) }
            )
            if (index < forecastData.size - 1) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )
            }
        }
    }
}

@Composable
private fun ForecastItem(
    forecast: ForecastItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            )
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = getFormattedDate(forecast.dt),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = forecast.weather.firstOrNull()?.description?.capitalize() ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WeatherIcon(
                weather = forecast.weather.firstOrNull(),
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = "${forecast.main.temp.roundToInt()}°F",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun WeatherIcon(weather: Weather?, modifier: Modifier = Modifier.size(120.dp)) {
    weather?.let { 
        val iconUrl = "https://openweathermap.org/img/wn/${weather.icon}@4x.png"
        AsyncImage(
            model = iconUrl,
            contentDescription = weather.description,
            modifier = modifier
        )
    }
}

private fun getDateOnly(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date(timestamp * 1000))
}

private fun getFormattedDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
    return if (timestamp == 0L) {
        sdf.format(Date())  // Current date for real-time weather
    } else {
        sdf.format(Date(timestamp * 1000))  // Convert forecast timestamp to date
    }
}

private fun String.capitalize(): String {
    return this.replaceFirstChar { 
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() 
    }
} 