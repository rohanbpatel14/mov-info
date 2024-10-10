package com.example.moviereview.data.datasource.remote

import com.example.moviereview.data.models.*
import com.example.moviereview.data.remote.TMDBService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteMovieReviewRemoteDataSource @Inject constructor(private val tmdbService: TMDBService) : MovieReviewRemoteDataSource {
    override suspend fun getPopularMovies(): PopularMoviesResponse {
        return withContext(Dispatchers.IO) {
            tmdbService.getPopularMovies()
        }
    }

    override suspend fun getPopularTvShows(): PopularTvShowsResponse {
        return withContext(Dispatchers.IO) {
            tmdbService.getPopularTvShows()
        }
    }

    override suspend fun getTrendingMedia(type: String, time: String): TrendingMediaResponse {
        return withContext(Dispatchers.IO) {
            tmdbService.getTrendingMediaByTime(type, time)
        }
    }

    override suspend fun getTvShowDetailById(id: Int): TvShowDetailResponse {
        return withContext(Dispatchers.IO) {
            tmdbService.getTvShowDetailById(id)
        }
    }

    override suspend fun getMovieDetailById(id: Int): MovieDetailResponse {
        return withContext(Dispatchers.IO) {
            tmdbService.getMovieById(id)
        }
    }

    override suspend fun getTvShowSeasonEpisodes(tvShowId: Int, seasonNum: Int): TvShowSeasonEpisodesResponse {
        return withContext(Dispatchers.IO) {
            tmdbService.getTvShowSeasonEpisodes(tvShowId, seasonNum)
        }
    }

    override suspend fun getDiscoverTvShows(page: Int, genres: String, networks: String): DiscoverTvShowResponse {
        return withContext(Dispatchers.IO) {
            tmdbService.getDiscoverTvShow(page, genres, networks)
        }
    }

    override suspend fun getDiscoverMovies(page: Int, genres: String, networks: String): DiscoverMovieResponse {
        return withContext(Dispatchers.IO) {
            tmdbService.getDiscoverMovies(page, genres, networks)
        }
    }
}