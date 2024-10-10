package com.example.moviereview.ui.views.tvshowdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviereview.domain.interactors.TvShowDetailInteractor
import com.example.moviereview.ui.models.UITvShowDetail
import com.example.moviereview.ui.utils.LiveDataStatus
import kotlinx.coroutines.launch

class TvShowDetailViewModel @ViewModelInject constructor(private val tvShowDetailInteractor: TvShowDetailInteractor) : ViewModel() {

    val tvShowDetailInfo: LiveDataStatus<UITvShowDetail> = tvShowDetailInteractor.tvShowDetailInfo
    val isTvShowsSaved: LiveData<Boolean> = tvShowDetailInteractor.isTvShowSaved

    fun getTvShowDetailInfo(id: Int) {
        viewModelScope.launch {
            tvShowDetailInteractor.retrieve(id)
        }
    }

    fun saveRemoveTvShow() {
        viewModelScope.launch {
            tvShowDetailInteractor.saveOrRemoveFromList()
        }
    }
}