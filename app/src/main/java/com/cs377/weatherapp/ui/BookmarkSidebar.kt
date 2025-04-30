package com.cs377.weatherapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cs377.weatherapp.data.BookmarkedLocation
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset

@Composable
fun BookmarkSidebar(
    bookmarkedLocations: List<BookmarkedLocation>,
    onLocationClick: (BookmarkedLocation) -> Unit,
    onRemoveBookmark: (BookmarkedLocation) -> Unit,
    onClose: () -> Unit
) {
    val density = LocalDensity.current
    val width = with(density) { 280.dp.toPx() }
    val offset = remember { Animatable(width) }
    
    LaunchedEffect(Unit) {
        offset.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 300)
        )
    }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Semi-transparent background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable(onClick = onClose)
        )
        
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(280.dp)
                .offset { IntOffset(offset.value.toInt(), 0) },
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Bookmarked Locations",
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close sidebar"
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                if (bookmarkedLocations.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No bookmarked locations",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(bookmarkedLocations) { location ->
                            BookmarkItem(
                                location = location,
                                onClick = { onLocationClick(location) },
                                onRemove = { onRemoveBookmark(location) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookmarkItem(
    location: BookmarkedLocation,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = location.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = location.country,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = "Remove bookmark",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
} 