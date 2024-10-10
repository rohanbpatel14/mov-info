package com.example.moviereview.data.models


import com.google.gson.annotations.SerializedName

data class TrendingMediaResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val mediaList: List<Media>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)