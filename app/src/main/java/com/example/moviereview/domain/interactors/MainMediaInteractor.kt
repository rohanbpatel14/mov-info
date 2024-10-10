package com.example.moviereview.domain.interactors

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.moviereview.data.repository.TMDBRepositoryImpl
import com.example.moviereview.domain.mappers.UIMediaMapper
import com.example.moviereview.ui.models.UIBackdropMedia
import com.example.moviereview.ui.models.UIPosterMedia
import com.example.moviereview.ui.utils.DataStatus
import com.example.moviereview.ui.utils.LiveDataStatus
import com.example.moviereview.ui.utils.MutableLiveDataStatus
import com.example.moviereview.ui.utils.TripleLiveData
import javax.inject.Inject

class MainMediaInteractor @Inject constructor(
    private val repository: TMDBRepositoryImpl,
    private val UIMediaMapper: UIMediaMapper
) {
    private val _trendingMediaToday = MutableLiveDataStatus<List<UIBackdropMedia>>()
    val trendingMediaToday: LiveDataStatus<List<UIBackdropMedia>> = _trendingMediaToday

    private val _popularMovies = MutableLiveDataStatus<List<UIPosterMedia>>()
    val popularMovies: LiveDataStatus<List<UIPosterMedia>> = _popularMovies

    private val _popularTvShows = MutableLiveDataStatus<List<UIPosterMedia>>()
    val popularTvShows: LiveDataStatus<List<UIPosterMedia>> = _popularTvShows

    val retrieveStatus: LiveData<DataStatus.Status> = Transformations.map(TripleLiveData(_trendingMediaToday, _popularMovies, _popularTvShows)) {
        val popularMoviesStatus = it.first?.status ?: DataStatus.Status.LOADING
        val popularTvShowsStatus = it.second?.status ?: DataStatus.Status.LOADING
        val trendingMediaStatus = it.third?.status ?: DataStatus.Status.LOADING

        val status = listOf(popularMoviesStatus, popularTvShowsStatus, trendingMediaStatus)

        when {
            status.size == status.count { it == DataStatus.Status.LOADING } -> DataStatus.Status.LOADING
            status.size == status.count { it == DataStatus.Status.ERROR } -> DataStatus.Status.ERROR
            status.count { it == DataStatus.Status.SUCCESS } > 0 -> DataStatus.Status.SUCCESS
            else -> DataStatus.Status.LOADING
        }
    }

    suspend fun retrieveAllMedia() {
        getTrendingMediaToday()
        getPopularTvShows()
        getPopularMovies()
    }

    private suspend fun getTrendingMediaToday() {
        _trendingMediaToday.postLoading()
        try {
            val trendingMedia = repository.getTrendingMediaByTime("all", "day")
            val uiTrendingMedia = trendingMedia.mediaList.map { UIMediaMapper.map(it) }
            _trendingMediaToday.postSuccess(uiTrendingMedia)
        } catch (e: Exception) {
            _trendingMediaToday.postError(e)
        }
    }

    private suspend fun getPopularMovies() {
        _popularMovies.postLoading()
        try {
            val popularMovies = repository.getPopularMovies()
            val uiMovies = popularMovies.movies.map { UIMediaMapper.map(it) }
            _popularMovies.postSuccess(uiMovies)
        } catch (e: Exception) {
            _popularMovies.postError(e)
        }
    }

    private suspend fun getPopularTvShows() {
        _popularTvShows.postLoading()
        try {
            val popularTvShows = repository.getPopularTvShows()
            val uiTvShows = popularTvShows.tvShows.map { UIMediaMapper.map(it) }
            _popularTvShows.postSuccess(uiTvShows)
        } catch (e: Exception) {
            _popularTvShows.postError(e)
        }
    }

}