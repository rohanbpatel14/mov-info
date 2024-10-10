package com.example.moviereview.data.repository

import com.example.moviereview.data.cache.models.MediaEntity
import com.example.moviereview.data.datasource.cache.CacheMovieReviewDataSource
import com.example.moviereview.data.datasource.remote.RemoteMovieReviewRemoteDataSource
import com.example.moviereview.data.models.*
import javax.inject.Inject

class TMDBRepositoryImpl @Inject constructor(
    private val cacheTMDBDataSource: CacheMovieReviewDataSource,
    private val remoteDataSource: RemoteMovieReviewRemoteDataSource
) : TMDBRepository {

    override suspend fun getPopularMovies(): PopularMoviesResponse {
        return remoteDataSource.getPopularMovies()
    }

    override suspend fun getPopularTvShows(): PopularTvShowsResponse {
        return remoteDataSource.getPopularTvShows()
    }

    override suspend fun getTrendingMediaByTime(type: String, time: String): TrendingMediaResponse {
        return remoteDataSource.getTrendingMedia(type, time)
    }

    override suspend fun getTvShowDetailById(id: Int): TvShowDetailResponse {
        return remoteDataSource.getTvShowDetailById(id)
    }

    override suspend fun getMovieDetailById(id: Int): MovieDetailResponse {
        return remoteDataSource.getMovieDetailById(id)
    }

    override suspend fun getTvShowSeasonEpisodes(tvShowId: Int, seasonNum: Int): TvShowSeasonEpisodesResponse {
        return remoteDataSource.getTvShowSeasonEpisodes(tvShowId, seasonNum)
    }

    override suspend fun getDiscoverTvShows(page: Int, genres: String, networks: String): DiscoverTvShowResponse {
        return remoteDataSource.getDiscoverTvShows(page, genres, networks)
    }

    override suspend fun getDiscoverMovies(page: Int, genres: String, networks: String): DiscoverMovieResponse {
        return remoteDataSource.getDiscoverMovies(page, genres, networks)
    }

    override suspend fun checkIfMediaWithId(id: Int): Boolean {
        return cacheTMDBDataSource.checkIfMediaWithId(id)
    }

    override suspend fun saveTvShow(tvShowDetail: TvShowDetailResponse) {
        cacheTMDBDataSource.saveTvShow(tvShowDetail)
    }

    override suspend fun saveMovie(movieDetail: MovieDetailResponse) {
        cacheTMDBDataSource.saveMovie(movieDetail)
    }

    override suspend fun removeMediaById(id: Int) {
        cacheTMDBDataSource.removeMedia(id)
    }

    override suspend fun getSavedMedia(): List<MediaEntity> {
        return cacheTMDBDataSource.getSavedMedia()
    }

}