package com.example.moviereview.ui.views.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviereview.domain.interactors.MainMediaInteractor
import com.example.moviereview.ui.models.UIBackdropMedia
import com.example.moviereview.ui.models.UIPosterMedia
import com.example.moviereview.ui.utils.DataStatus
import com.example.moviereview.ui.utils.LiveDataStatus
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(mainMediaInteractor: MainMediaInteractor) : ViewModel() {

    val trendingMediaToday: LiveDataStatus<List<UIBackdropMedia>> = mainMediaInteractor.trendingMediaToday
    val popularMovies: LiveDataStatus<List<UIPosterMedia>> = mainMediaInteractor.popularMovies
    val popularTvShows: LiveDataStatus<List<UIPosterMedia>> = mainMediaInteractor.popularTvShows
    val viewState: LiveData<DataStatus.Status> = mainMediaInteractor.retrieveStatus

    init {
        viewModelScope.launch {
            mainMediaInteractor.retrieveAllMedia()
        }
    }
}