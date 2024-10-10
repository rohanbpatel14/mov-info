package com.example.moviereview.ui.views.discover

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviereview.domain.interactors.DiscoverMediaInteractor
import com.example.moviereview.domain.interactors.DiscoverMediaInteractor.Companion.MOVIE_TYPE_ID
import com.example.moviereview.domain.interactors.DiscoverMediaInteractor.Companion.TV__TYPE_ID
import com.example.moviereview.ui.models.UIFilter
import com.example.moviereview.ui.models.UIMedia
import com.example.moviereview.ui.utils.LiveDataStatus
import com.example.moviereview.ui.utils.MutableLiveDataStatus
import kotlinx.coroutines.launch

class DiscoverViewModel @ViewModelInject constructor(private val discoverMediaInteractor: DiscoverMediaInteractor) : ViewModel() {

    private val _discoverMedia = MutableLiveDataStatus<List<UIMedia>>()
    val discoverMedia: LiveDataStatus<List<UIMedia>> = _discoverMedia

    private val _updateMedia = MutableLiveDataStatus<List<UIMedia>>()
    val updateMedia: LiveDataStatus<List<UIMedia>> = _updateMedia

    private val _showFilters = MutableLiveData<Boolean>(false)
    val showFilters: LiveData<Boolean> = _showFilters

    private val _filterTypeList = MutableLiveData<List<UIFilter>>()
    val filterTypeList: LiveData<List<UIFilter>> = _filterTypeList

    private val _filterGenreList = MutableLiveData<List<UIFilter>>()
    val filterGenreList: LiveData<List<UIFilter>> = _filterGenreList

    private val _filterNetworkList = MutableLiveData<List<UIFilter>>()
    val filterNetworkList: LiveData<List<UIFilter>> = _filterNetworkList

    private val _scrollToTop = MutableLiveData<Boolean>()
    val scrollToTop: LiveData<Boolean> = _scrollToTop

    private var networkList = discoverMediaInteractor.networkList
    private var genreList = discoverMediaInteractor.genreList
    private var typeList = discoverMediaInteractor.typeList

    private var currentMoviePage: Int = 1
    private var currentTvPage: Int = 1

    init {
        _filterNetworkList.postValue(discoverMediaInteractor.networkList)
        _filterGenreList.postValue(discoverMediaInteractor.genreList)
        _filterTypeList.postValue(discoverMediaInteractor.typeList)
        initializeDiscoverMedia()
    }

    fun selectedType(item: UIFilter) {
        typeList.first { it.id == item.id }.selected = !typeList.first { it.id == item.id }.selected
        _filterTypeList.postValue(typeList)
    }

    fun selectedGenre(item: UIFilter) {
        genreList.first { it.id == item.id }.selected = !genreList.first { it.id == item.id }.selected
        _filterGenreList.postValue(genreList)
    }

    fun selectedNetwork(item: UIFilter) {
        networkList.first { it.id == item.id }.selected = !networkList.first { it.id == item.id }.selected
        _filterNetworkList.postValue(networkList)
    }

    fun showHideFilters() {
        _showFilters.postValue(!(showFilters.value ?: true))
    }

    fun hideFilters() {
        _showFilters.postValue(false)
        _scrollToTop.postValue(false)
    }

    fun searchFilters() {
        _showFilters.postValue(!(showFilters.value ?: true))
        fetchMediaWithFilters()
    }

    private fun initializeDiscoverMedia() {
        _discoverMedia.postLoading()
        viewModelScope.launch {
            try {
                val media = getDiscoverMedia()
                if (media.isNotEmpty()) {
                    _discoverMedia.postSuccess(media)
                } else {
                    _discoverMedia.postEmpty()
                }
            } catch (e: Exception) {
                _discoverMedia.postError(e)
            }
        }
    }


    private fun fetchMediaWithFilters() {
        _discoverMedia.postLoading()
        viewModelScope.launch {
            try {
                currentTvPage = 1
                currentMoviePage = 1

                val media = getDiscoverMedia()

                if (media.isNotEmpty()) {
                    _discoverMedia.postSuccess(media)
                    _scrollToTop.postValue(true)
                } else {
                    _discoverMedia.postEmpty()
                }
            } catch (e: Exception) {
                _discoverMedia.postError(e)
            }
        }
    }

    fun retrieveNewMedia() {
        viewModelScope.launch {
            try {
                _updateMedia.postSuccess(getDiscoverMedia())
            } catch (e: Exception) {
                _updateMedia.postError(e)
            }
        }
    }

    private suspend fun getDiscoverMedia(): List<UIMedia> {
        val discoverMedia = mutableListOf<UIMedia>()

        val tvSelected = typeList.first { it.id == TV__TYPE_ID }.selected
        val movieSelected = typeList.first { it.id == MOVIE_TYPE_ID }.selected

        val genres = genreList.filter { it.selected }.map { it.id }.joinToString("|", prefix = "", postfix = "")
        val networks = networkList.filter { it.selected }.map { it.id }.joinToString("|", prefix = "", postfix = "")

        when {
            !movieSelected && !tvSelected -> {
                discoverMedia.addAll(discoverMediaInteractor.getDiscoverTvShows(currentTvPage, genres, networks))
                discoverMedia.addAll(discoverMediaInteractor.getDiscoverMovies(currentMoviePage, genres, networks))

                currentMoviePage += 1
                currentTvPage += 1
            }

            movieSelected -> {
                discoverMedia.addAll(discoverMediaInteractor.getDiscoverMovies(currentMoviePage, genres, networks))
                currentMoviePage += 1
            }

            tvSelected -> {
                discoverMedia.addAll(discoverMediaInteractor.getDiscoverTvShows(currentTvPage, genres, networks))
                currentTvPage += 1
            }
        }

        return discoverMedia.sortedBy { it.voteAverage }
    }

}