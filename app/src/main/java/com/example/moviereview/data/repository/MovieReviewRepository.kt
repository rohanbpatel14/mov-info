package com.example.moviereview.data.repository

import com.example.moviereview.data.cache.models.MediaEntity
import com.example.moviereview.data.models.*

interface TMDBRepository {
    suspend fun getPopularMovies(): PopularMoviesResponse
    suspend fun getPopularTvShows(): PopularTvShowsResponse
    suspend fun getTrendingMediaByTime(type: String, time: String): TrendingMediaResponse
    suspend fun getTvShowDetailById(id: Int): TvShowDetailResponse
    suspend fun getMovieDetailById(id: Int): MovieDetailResponse
    suspend fun getTvShowSeasonEpisodes(tvShowId: Int, seasonNum: Int): TvShowSeasonEpisodesResponse
    suspend fun getDiscoverTvShows(page: Int, genres: String, networks: String): DiscoverTvShowResponse
    suspend fun getDiscoverMovies(page: Int, genres: String, networks: String): DiscoverMovieResponse

    suspend fun checkIfMediaWithId(id: Int): Boolean
    suspend fun saveMovie(movieDetail: MovieDetailResponse)
    suspend fun saveTvShow(tvShowDetail: TvShowDetailResponse)
    suspend fun getSavedMedia(): List<MediaEntity>
    suspend fun removeMediaById(id: Int)
}
