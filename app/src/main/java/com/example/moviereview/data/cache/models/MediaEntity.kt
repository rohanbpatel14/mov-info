package com.example.moviereview.data.cache.models

import androidx.room.Entity

@Entity(tableName = "media", primaryKeys = ["id"])
class MediaEntity(
    val id: Int,
    val type: String,
    val name: String,
    val date: String,
    val overview: String,
    val voteAverage: Double,
    val posterPath: String,
    val backdropPath: String
)