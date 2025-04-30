package com.cs377.weatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cs377.weatherapp.data.ForecastItem
import com.cs377.weatherapp.data.ForecastResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Composable
fun ForecastList(forecastData: ForecastResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "5-Day Forecast",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        val dailyForecasts = processForecastData(forecastData.list)
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(dailyForecasts) { forecast ->
                ForecastItem(forecast)
            }
        }
    }
}

@Composable
fun ForecastItem(forecast: DailyForecast) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = forecast.day,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            AsyncImage(
                model = "https://openweathermap.org/img/wn/${forecast.weatherIcon}@2x.png",
                contentDescription = forecast.weatherDescription,
                modifier = Modifier.size(64.dp)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "${forecast.maxTemp.roundToInt()}°",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Text(
                text = "${forecast.minTemp.roundToInt()}°",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

data class DailyForecast(
    val date: Date,
    val day: String,
    val minTemp: Double,
    val maxTemp: Double,
    val weatherIcon: String,
    val weatherDescription: String
)

fun processForecastData(forecastItems: List<ForecastItem>): List<DailyForecast> {
    // Group forecast items by day
    val dailyForecasts = mutableMapOf<String, MutableList<ForecastItem>>()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dayOfWeekFormat = SimpleDateFormat("EEE", Locale.getDefault())
    
    forecastItems.forEach { item ->
        val date = Date(item.dt * 1000)
        val dateString = dateFormat.format(date)
        
        if (!dailyForecasts.containsKey(dateString)) {
            dailyForecasts[dateString] = mutableListOf()
        }
        dailyForecasts[dateString]?.add(item)
    }
    
    // Process each day to get min/max temps and represent weather
    return dailyForecasts.map { (dateString, items) ->
        val date = dateFormat.parse(dateString) ?: Date()
        val dayOfWeek = dayOfWeekFormat.format(date)
        
        // Get min and max temperatures for the day
        val minTemp = items.minOfOrNull { it.main.temp_min } ?: 0.0
        val maxTemp = items.maxOfOrNull { it.main.temp_max } ?: 0.0
        
        // Use noon forecast for the day's weather icon, or the middle of the day
        val middayIndex = (items.size / 2).coerceAtMost(items.size - 1)
        val middayForecast = items[middayIndex]
        val weatherIcon = middayForecast.weather.firstOrNull()?.icon ?: ""
        val weatherDescription = middayForecast.weather.firstOrNull()?.description ?: ""
        
        DailyForecast(
            date = date,
            day = dayOfWeek,
            minTemp = minTemp,
            maxTemp = maxTemp,
            weatherIcon = weatherIcon,
            weatherDescription = weatherDescription
        )
    }.take(5) // Ensure we only return 5 days
} 