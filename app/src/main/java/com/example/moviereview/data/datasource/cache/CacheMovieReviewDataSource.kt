package com.example.moviereview.data.datasource.cache

import com.example.moviereview.data.cache.MovieReviewDatabase
import com.example.moviereview.data.cache.models.MediaEntity
import com.example.moviereview.data.mappers.MediaMapper
import com.example.moviereview.data.models.MovieDetailResponse
import com.example.moviereview.data.models.TvShowDetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CacheMovieReviewDataSource @Inject constructor(
    private val movieReviewDatabase: MovieReviewDatabase,
    private val mediaMapper: MediaMapper
) : MovieReviewCacheDataSource {

    override suspend fun saveTvShow(tvShowDetail: TvShowDetailResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            movieReviewDatabase.mediaDAO().insertNewMedia(mediaMapper.map(tvShowDetail))
        }
    }

    override suspend fun saveMovie(movieDetail: MovieDetailResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            movieReviewDatabase.mediaDAO().insertNewMedia(mediaMapper.map(movieDetail))
        }
    }

    override suspend fun getSavedMedia(): List<MediaEntity> {
        return withContext(Dispatchers.IO) {
            movieReviewDatabase.mediaDAO().getAllMedia()
        }
    }

    override suspend fun removeMedia(id: Int) {
        return withContext(Dispatchers.IO) {
            movieReviewDatabase.mediaDAO().removeMediaWithId(id)
        }
    }

    override suspend fun checkIfMediaWithId(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            movieReviewDatabase.mediaDAO().checkIfMediaWithId(id)
        }
    }
}