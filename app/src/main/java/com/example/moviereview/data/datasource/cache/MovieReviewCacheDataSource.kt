package com.example.moviereview.data.datasource.cache

import com.example.moviereview.data.cache.models.MediaEntity
import com.example.moviereview.data.models.MovieDetailResponse
import com.example.moviereview.data.models.TvShowDetailResponse

interface MovieReviewCacheDataSource {
    suspend fun saveTvShow(tvShowDetail: TvShowDetailResponse)
    suspend fun saveMovie(movieDetailResponse: MovieDetailResponse)
    suspend fun getSavedMedia(): List<MediaEntity>
    suspend fun checkIfMediaWithId(id: Int): Boolean
    suspend fun removeMedia(id: Int)
}