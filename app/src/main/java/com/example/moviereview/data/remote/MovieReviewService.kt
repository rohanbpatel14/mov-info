package com.example.moviereview.data.remote

import com.example.moviereview.data.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {
    @GET("movie/popular/")
    suspend fun getPopularMovies(): PopularMoviesResponse

    @GET("tv/popular/")
    suspend fun getPopularTvShows(): PopularTvShowsResponse

    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrendingMediaByTime(@Path("media_type") type: String, @Path("time_window") time: String): TrendingMediaResponse

    @GET("tv/{tv_id}")
    suspend fun getTvShowDetailById(@Path("tv_id") tvShowId: Int, @Query("append_to_response") append: String = "credits,similar,recommendations,images"): TvShowDetailResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieById(@Path("movie_id") tvShowId: Int, @Query("append_to_response") append: String = "credits,similar,recommendations,images"): MovieDetailResponse

    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun getTvShowSeasonEpisodes(@Path("tv_id") tvShowId: Int, @Path("season_number") seasonNumber: Int): TvShowSeasonEpisodesResponse

    @GET("discover/tv")
    suspend fun getDiscoverTvShow(
        @Query("page") page: Int = 1,
        @Query("with_genres") genres: String = "",
        @Query("with_networks") networks: String = ""
    ): DiscoverTvShowResponse

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("page") page: Int = 1,
        @Query("with_genres") genres: String = "",
        @Query("with_networks") networks: String = ""
    ): DiscoverMovieResponse
}