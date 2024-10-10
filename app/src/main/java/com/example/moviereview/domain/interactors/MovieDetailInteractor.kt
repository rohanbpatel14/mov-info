package com.example.moviereview.domain.interactors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviereview.data.models.MovieDetailResponse
import com.example.moviereview.data.repository.TMDBRepositoryImpl
import com.example.moviereview.domain.mappers.UIMediaMapper
import com.example.moviereview.ui.models.UIMovieDetail
import com.example.moviereview.ui.utils.LiveDataStatus
import com.example.moviereview.ui.utils.MutableLiveDataStatus
import javax.inject.Inject

class MovieDetailInteractor @Inject constructor(private val repository: TMDBRepositoryImpl, private val uIMediaMapper: UIMediaMapper) {

    private val _movieDetailInfo = MutableLiveDataStatus<UIMovieDetail>()
    val movieDetailInfo: LiveDataStatus<UIMovieDetail> = _movieDetailInfo

    private val _isMovieSaved = MutableLiveData(false)
    val isMovieSaved: LiveData<Boolean> = _isMovieSaved

    private var movieDetailResponse: MovieDetailResponse? = null
    private var isSaved = false

    suspend fun retrieve(id: Int) {
        _movieDetailInfo.postLoading()
        try {
            movieDetailResponse = repository.getMovieDetailById(id)
            movieDetailResponse?.let {
                val uiMovieDetail = uIMediaMapper.map(it)
                uiMovieDetail.saved = repository.checkIfMediaWithId(id)
                _movieDetailInfo.postSuccess(uiMovieDetail)
                isSaved = uiMovieDetail.saved
            }
        } catch (e: Exception) {
            _movieDetailInfo.postError(e)
        }
    }

    suspend fun saveOrRemoveFromList() {
        try {
            movieDetailResponse?.let {
                if (!isSaved) {
                    repository.saveMovie(it)
                    _isMovieSaved.postValue(true)
                } else {
                    repository.removeMediaById(it.id)
                    _isMovieSaved.postValue(false)
                }
                isSaved = !isSaved
            }
        } catch (e: Exception) {

        }
    }


}