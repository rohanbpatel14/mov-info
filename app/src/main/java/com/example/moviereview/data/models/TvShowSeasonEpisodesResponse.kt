package com.example.moviereview.data.models


import com.google.gson.annotations.SerializedName

data class TvShowSeasonEpisodesResponse(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episodes")
    val episodes: List<Episode>,
    @SerializedName("name")
    val name: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("season_number")
    val seasonNumber: Int
)