package com.example.moviereview.ui.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.moviereview.R

private const val basePosterURL = "https://image.tmdb.org/t/p/w300"
private const val baseBackdropURL = "https://image.tmdb.org/t/p/w780"

@BindingAdapter("posterImage")
fun ImageView.bindPosterImage(posterPath: String?) {
    posterPath?.let {
        Glide.with(this.context).load(basePosterURL + posterPath).placeholder(R.drawable.poster_placeholder).into(this)
    } ?: kotlin.run {
        Glide.with(this.context).load(R.drawable.poster_placeholder).into(this)
    }
}

@BindingAdapter("backdropImage")
fun ImageView.bindBackdropImage(backdropPath: String?) {
    backdropPath?.let {
        Glide.with(this.context).load(baseBackdropURL + backdropPath).placeholder(R.drawable.backdrop_placeholder).into(this)
    } ?: kotlin.run {
        Glide.with(this.context).load(R.drawable.backdrop_placeholder).into(this)
    }
}

@BindingAdapter("savedStatusImg")
fun ImageView.bindSavedStatusImg(saved: Boolean?) {
    if (saved == true) {
        this.setImageResource(R.drawable.ic_saved_filled)
    } else {
        this.setImageResource(R.drawable.ic_saved_border)
    }
}

@BindingAdapter("mediaType")
fun ImageView.mediaType(mediaType: String?) {
    mediaType?.let {
        if (it == "tv") {
            this.setImageResource(R.drawable.ic_tv_show)
        } else if (it == "movie") {
            this.setImageResource(R.drawable.ic_movie)
        }
        this.imageTintList = ContextCompat.getColorStateList(context, R.color.colorDarkWhite)
        this.backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorPrimary)
    }
}

@BindingAdapter("isSelected")
fun TextView.isSelected(selected: Boolean?) {
    selected?.let {
        if (it) {
            this.backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorPrimary)
            this.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        } else {
            this.backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorLightGrey)
            this.setTextColor(ContextCompat.getColor(context, R.color.colorDarkGrey))
        }
    }
}