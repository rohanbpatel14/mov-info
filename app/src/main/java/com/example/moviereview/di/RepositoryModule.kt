package com.example.moviereview.di

import com.example.moviereview.data.datasource.remote.RemoteMovieReviewRemoteDataSource
import com.example.moviereview.data.datasource.remote.MovieReviewRemoteDataSource
import com.example.moviereview.data.repository.TMDBRepository
import com.example.moviereview.data.repository.TMDBRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesRemoteDataSource(remoteDataSource: RemoteMovieReviewRemoteDataSource): MovieReviewRemoteDataSource = remoteDataSource

    @Provides
    @Singleton
    fun provideRepository(repository: TMDBRepositoryImpl): TMDBRepository = repository

}