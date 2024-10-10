package com.example.moviereview.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app
}