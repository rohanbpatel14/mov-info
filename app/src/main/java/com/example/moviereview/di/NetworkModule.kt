package com.example.moviereview.di

import com.example.moviereview.BuildConfig
import com.example.moviereview.data.remote.TMDBService
import com.example.moviereview.di.annotations.LoggingInterceptor
import com.example.moviereview.di.annotations.RequestInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {
    companion object {
        private const val TMDB_URL = "https://api.themoviedb.org/3/"
    }

    @Provides
    fun provideMovieDbService(okHttpClient: OkHttpClient): TMDBService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(TMDB_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(TMDBService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @RequestInterceptor requestInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @RequestInterceptor
    fun provideRequestInterceptor(): Interceptor {
        return Interceptor { chain ->
            val url = chain.request().url.newBuilder().addQueryParameter("api_key", BuildConfig.TMDB_KEY).build()
            val request = chain.request().newBuilder().url(url).build()
            chain.proceed(request)
        }
    }

    @Provides
    @LoggingInterceptor
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.BUILD_TYPE == "debug") level = HttpLoggingInterceptor.Level.BODY
        }
    }
}