package com.cs377.weatherapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Query("SELECT * FROM bookmarked_locations ORDER BY name ASC")
    fun getAllLocations(): Flow<List<BookmarkedLocation>>

    @Insert
    suspend fun insertLocation(location: BookmarkedLocation)

    @Delete
    suspend fun deleteLocation(location: BookmarkedLocation)

    @Query("SELECT * FROM bookmarked_locations WHERE name = :name")
    suspend fun getLocationByName(name: String): BookmarkedLocation?
} 