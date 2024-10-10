package com.example.moviereview.data.models


import com.google.gson.annotations.SerializedName

data class GuestStar(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("credit_id")
    val creditId: String,
    @SerializedName("character")
    val character: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("profile_path")
    val profilePath: String
)