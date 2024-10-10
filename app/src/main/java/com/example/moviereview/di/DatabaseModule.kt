package com.example.moviereview.di

import android.content.Context
import com.example.moviereview.data.cache.MovieReviewDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MovieReviewDatabase {
        return MovieReviewDatabase.invoke(context)
    }
}