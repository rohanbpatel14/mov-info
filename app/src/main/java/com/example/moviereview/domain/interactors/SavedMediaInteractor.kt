package com.example.moviereview.domain.interactors

import com.example.moviereview.data.repository.TMDBRepositoryImpl
import com.example.moviereview.domain.mappers.UIMediaMapper
import com.example.moviereview.ui.models.UIMedia
import javax.inject.Inject

class SavedMediaInteractor @Inject constructor(private val repository: TMDBRepositoryImpl, private val uIMediaMapper: UIMediaMapper) {

    suspend operator fun invoke(): List<UIMedia> {
        return repository.getSavedMedia().map { uIMediaMapper.map(it) }
    }

}
