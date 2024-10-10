package com.example.moviereview.ui.views.tvshowdetail.seasonsepisodes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviereview.domain.interactors.TvShowDetailInteractor
import com.example.moviereview.ui.models.UIEpisode
import com.example.moviereview.ui.utils.LiveDataStatus
import kotlinx.coroutines.launch

class TvShowSeasonEpisodesViewModel @ViewModelInject constructor(private val tvShowDetailInteractor: TvShowDetailInteractor) : ViewModel() {
    val seasonEpisodes: LiveDataStatus<List<UIEpisode>> = tvShowDetailInteractor.seasonEpisodes

    fun getTvShowSeasonEpisodes(tvShowId: Int, seasonNum: Int) {
        viewModelScope.launch {
            tvShowDetailInteractor.retrieveSeasonEpisodes(tvShowId, seasonNum)
        }
    }
}