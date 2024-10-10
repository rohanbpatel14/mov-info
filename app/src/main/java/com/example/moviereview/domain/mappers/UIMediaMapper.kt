package com.example.moviereview.domain.mappers

import com.example.moviereview.data.cache.models.MediaEntity
import com.example.moviereview.data.models.*
import com.example.moviereview.ui.models.*
import javax.inject.Inject

class UIMediaMapper @Inject constructor() {

    fun map(movie: Movie): UIPosterMedia {
        return UIPosterMedia(
            movie.id,
            movie.voteAverage,
            movie.title,
            movie.backdropPath,
            movie.posterPath
        )
    }

    fun map(tvShow: TvShow): UIPosterMedia {
        return UIPosterMedia(
            tvShow.id,
            tvShow.voteAverage,
            tvShow.name,
            tvShow.backdropPath,
            tvShow.posterPath
        )
    }

    fun map(media: Media): UIBackdropMedia {
        return UIBackdropMedia(
            media.id,
            media.voteAverage,
            if (media.mediaType == "movie") media.title else if (media.mediaType == "tv") media.name else "",
            media.backdropPath,
            media.posterPath,
            media.mediaType
        )
    }

    fun map(result: Result, type: String): UIPosterMedia {
        return UIPosterMedia(
            result.id,
            result.voteAverage,
            if (type == "movie") result.title else result.name,
            result.backdropPath,
            result.posterPath
        )
    }

    fun map(cast: Cast): UICast {
        return UICast(
            cast.id,
            cast.name,
            cast.profilePath
        )
    }

    fun map(season: Season): UISeason {
        return UISeason(
            season.id,
            season.episodeCount,
            season.name,
            season.overview,
            season.posterPath,
            season.seasonNumber
        )
    }

    fun map(episode: Episode): UIEpisode {
        return UIEpisode(
            episode.id,
            episode.stillPath,
            episode.overview
        )
    }

    fun map(mediaEntity: MediaEntity): UIMedia {
        return UIMedia(
            mediaEntity.id,
            mediaEntity.name,
            mediaEntity.type,
            mediaEntity.overview,
            mediaEntity.posterPath,
            mediaEntity.voteAverage
        )
    }

    fun mapTvShowToMedia(tvShow: TvShow): UIMedia {
        return UIMedia(
            tvShow.id,
            tvShow.name,
            "tv",
            tvShow.overview,
            tvShow.posterPath,
            tvShow.voteAverage
        )
    }

    fun mapMovieToMedia(movie: Movie): UIMedia {
        return UIMedia(
            movie.id,
            movie.title,
            "movie",
            movie.overview,
            movie.posterPath,
            movie.voteAverage
        )
    }

    fun map(tvShowDetailResponse: TvShowDetailResponse): UITvShowDetail {
        return UITvShowDetail(
            tvShowDetailResponse.id,
            tvShowDetailResponse.name,
            tvShowDetailResponse.firstAirDate,
            tvShowDetailResponse.overview,
            tvShowDetailResponse.popularity,
            tvShowDetailResponse.posterPath,
            tvShowDetailResponse.backdropPath,
            tvShowDetailResponse.voteAverage,
            tvShowDetailResponse.voteCount,
            tvShowDetailResponse.genres.joinToString(separator = ",", prefix = "", postfix = "") { it.name },
            tvShowDetailResponse.similar.results.map { map(it, "tv") } + tvShowDetailResponse.recommendations.results.map { map(it, "tv") },
            tvShowDetailResponse.credits.cast.map { map(it) },
            tvShowDetailResponse.images.backdrops.map { UIImage(it.filePath) },
            tvShowDetailResponse.seasons.map { map(it) }
        )
    }

    fun map(movieDetailResponse: MovieDetailResponse): UIMovieDetail {
        return UIMovieDetail(
            movieDetailResponse.id,
            movieDetailResponse.title,
            movieDetailResponse.releaseDate,
            movieDetailResponse.overview,
            movieDetailResponse.popularity,
            movieDetailResponse.posterPath,
            movieDetailResponse.backdropPath,
            movieDetailResponse.voteAverage,
            movieDetailResponse.voteCount,
            movieDetailResponse.genres.joinToString(separator = ",", prefix = "", postfix = "") { it.name },
            movieDetailResponse.similar.results.map { map(it, "movie") } + movieDetailResponse.recommendations.results.map { map(it, "movie") },
            movieDetailResponse.credits.cast.map { map(it) },
            movieDetailResponse.images.backdrops.map { UIImage(it.filePath) }
        )
    }

    fun map(tvShowSeasonEpisodesResponse: TvShowSeasonEpisodesResponse): List<UIEpisode> {
        return tvShowSeasonEpisodesResponse.episodes.map { map(it) }
    }
}