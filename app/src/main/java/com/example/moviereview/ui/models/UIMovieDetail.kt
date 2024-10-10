package com.example.moviereview.ui.models

class UIMovieDetail(
    val id: Int,
    val name: String,
    val firstAirDate: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val genres: String,
    val similar: List<UIPosterMedia>,
    val cast: List<UICast>,
    val images: List<UIImage>,
    var saved: Boolean = false
)
