package com.example.moviereview.ui.views.moviedetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviereview.domain.interactors.MovieDetailInteractor
import com.example.moviereview.ui.models.UIMovieDetail
import com.example.moviereview.ui.utils.LiveDataStatus
import kotlinx.coroutines.launch

class MovieDetailViewModel @ViewModelInject constructor(private val movieDetailInteractor: MovieDetailInteractor) : ViewModel() {
    val movieDetailInfo: LiveDataStatus<UIMovieDetail> = movieDetailInteractor.movieDetailInfo
    val isMovieSaved: LiveData<Boolean> = movieDetailInteractor.isMovieSaved

    fun getMovieDetailInfo(id: Int) {
        viewModelScope.launch {
            movieDetailInteractor.retrieve(id)
        }
    }

    fun saveRemoveMovie() {
        viewModelScope.launch {
            movieDetailInteractor.saveOrRemoveFromList()
        }
    }
}