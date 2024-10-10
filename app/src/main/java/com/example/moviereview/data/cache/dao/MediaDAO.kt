package com.example.moviereview.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviereview.data.cache.models.MediaEntity

@Dao
interface MediaDAO {
    @Query("SELECT * FROM media")
    suspend fun getAllMedia(): List<MediaEntity>

    @Query("SELECT EXISTS(SELECT * FROM media WHERE id = :id)")
    suspend fun checkIfMediaWithId(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewMedia(media: MediaEntity)

    @Query("DELETE FROM media")
    suspend fun deleteAll()

    @Query("DELETE FROM media WHERE id = :id")
    suspend fun removeMediaWithId(id: Int)
}