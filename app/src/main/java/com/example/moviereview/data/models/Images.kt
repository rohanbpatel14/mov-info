package com.example.moviereview.data.models


import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("backdrops")
    val backdrops: List<ImageData>,
    @SerializedName("posters")
    val posters: List<ImageData>
)