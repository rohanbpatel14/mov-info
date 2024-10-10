package com.example.moviereview.domain.interactors

import com.example.moviereview.data.repository.TMDBRepositoryImpl
import com.example.moviereview.domain.mappers.UIMediaMapper
import com.example.moviereview.ui.models.UIFilter
import com.example.moviereview.ui.models.UIMedia
import javax.inject.Inject

class DiscoverMediaInteractor @Inject constructor(private val repository: TMDBRepositoryImpl, private val uiMediaMapper: UIMediaMapper) {

    suspend fun getDiscoverTvShows(page: Int = 1, genres: String = "", networks: String = ""): List<UIMedia> {
        val discoverTvShows = repository.getDiscoverTvShows(page, genres, networks)
        return discoverTvShows.results.map { uiMediaMapper.mapTvShowToMedia(it) }
    }

    suspend fun getDiscoverMovies(page: Int = 1, genres: String = "", networks: String = ""): List<UIMedia> {
        val discoverMovies = repository.getDiscoverMovies(page, genres, networks)
        return discoverMovies.results.map { uiMediaMapper.mapMovieToMedia(it) }
    }

    val networkList = listOf(
        UIFilter(213, "Netflix", false),
        UIFilter(49, "HBO", false),
        UIFilter(1024, "AmazonPrime", false),
        UIFilter(2252, "Apple Tv +", false),
        UIFilter(2739, "Disney Plus", false),
        UIFilter(3667, "Movistar +", false),
        UIFilter(453, "Hulu", false),
        UIFilter(1436, "YouTube Premium", false)
    )

    val genreList = listOf(
        UIFilter(28, "Action", false),
        UIFilter(12, "Adventure", false),
        UIFilter(16, "Animation", false),
        UIFilter(35, "Comedy", false),
        UIFilter(80, "Crime", false),
        UIFilter(99, "Documentary", false),
        UIFilter(18, "Drama", false),
        UIFilter(10751, "Family", false),
        UIFilter(14, "Fantasy", false),
        UIFilter(36, "History", false),
        UIFilter(27, "Horror", false),
        UIFilter(10402, "Music", false),
        UIFilter(9648, "Mystery", false),
        UIFilter(10749, "Romance", false),
        UIFilter(878, "Science Fiction", false),
        UIFilter(10770, "TV Movie", false),
        UIFilter(53, "Thriller", false),
        UIFilter(10752, "War", false),
        UIFilter(37, "Western", false),
        UIFilter(10759, "Action & Adventure", false),
        UIFilter(10762, "Kids", false),
        UIFilter(10763, "News", false),
        UIFilter(10764, "Reality", false),
        UIFilter(10765, "Sci-Fi & Fantasy", false),
        UIFilter(10766, "Soap", false),
        UIFilter(10767, "Talk", false),
        UIFilter(10768, "War & Politics", false)
    )

    val typeList = listOf(
        UIFilter(TV__TYPE_ID, "Tv shows", false),
        UIFilter(MOVIE_TYPE_ID, "Movies", false)
    )

    companion object {
        const val TV__TYPE_ID = -202
        const val MOVIE_TYPE_ID = -101
    }
}