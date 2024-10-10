package com.example.moviereview.domain.interactors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviereview.data.models.TvShowDetailResponse
import com.example.moviereview.data.repository.TMDBRepositoryImpl
import com.example.moviereview.domain.mappers.UIMediaMapper
import com.example.moviereview.ui.models.UIEpisode
import com.example.moviereview.ui.models.UITvShowDetail
import com.example.moviereview.ui.utils.LiveDataStatus
import com.example.moviereview.ui.utils.MutableLiveDataStatus
import javax.inject.Inject

class TvShowDetailInteractor @Inject constructor(private val repository: TMDBRepositoryImpl, private val uiMediaMapper: UIMediaMapper) {

    private val _tvShowDetailInfo = MutableLiveDataStatus<UITvShowDetail>()
    val tvShowDetailInfo: LiveDataStatus<UITvShowDetail> = _tvShowDetailInfo

    private val _isTvShowSaved = MutableLiveData(false)
    val isTvShowSaved: LiveData<Boolean> = _isTvShowSaved

    private val _seasonEpisodes = MutableLiveDataStatus<List<UIEpisode>>()
    val seasonEpisodes: LiveDataStatus<List<UIEpisode>> = _seasonEpisodes

    private var tvShowDetailResponse: TvShowDetailResponse? = null
    private var isSaved = false

    suspend fun retrieve(id: Int) {
        _tvShowDetailInfo.postLoading()
        try {
            tvShowDetailResponse = repository.getTvShowDetailById(id)
            tvShowDetailResponse?.let {
                val uiTvShowDetail = uiMediaMapper.map(it)
                uiTvShowDetail.saved = repository.checkIfMediaWithId(id)
                _tvShowDetailInfo.postSuccess(uiTvShowDetail)
                isSaved = uiTvShowDetail.saved
            }
        } catch (e: Exception) {
            _tvShowDetailInfo.postError(e)
        }
    }

    suspend fun retrieveSeasonEpisodes(tvShowId: Int, seasonNum: Int) {
        _seasonEpisodes.postLoading()

        try {
            val seasonEpisodes = repository.getTvShowSeasonEpisodes(tvShowId, seasonNum)
            val uiSeasonEpisodes = uiMediaMapper.map(seasonEpisodes)
            if (uiSeasonEpisodes.isNotEmpty()) {
                _seasonEpisodes.postSuccess(uiSeasonEpisodes)
            } else {
                _seasonEpisodes.postEmpty()
            }
        } catch (e: Exception) {
            _seasonEpisodes.postError(e)
        }
    }

    suspend fun saveOrRemoveFromList() {
        try {
            tvShowDetailResponse?.let {
                if (!isSaved) {
                    repository.saveTvShow(it)
                    _isTvShowSaved.postValue(true)
                } else {
                    repository.removeMediaById(it.id)
                    _isTvShowSaved.postValue(false)
                }
                isSaved = !isSaved
            }
        } catch (e: Exception) {
        }
    }
}
