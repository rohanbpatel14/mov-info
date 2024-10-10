package com.example.moviereview.data.models


import com.google.gson.annotations.SerializedName

data class Logo(
    @SerializedName("path")
    val path: String,
    @SerializedName("aspect_ratio")
    val aspectRatio: Double
)