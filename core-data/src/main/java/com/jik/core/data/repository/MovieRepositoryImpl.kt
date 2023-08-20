package com.jik.core.data.repository

import com.jik.core.model.Movie
import com.jik.core.model.MovieInfo
import com.jik.core.network.source.MovieRemoteDataSource
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return movieRemoteDataSource.getPopularMovies(page)
    }

    override suspend fun getMovieInfo(id: Long): Result<MovieInfo> {
        val movieInfo = movieRemoteDataSource.getMovieInfo(id = id)
        val movieCredits = movieRemoteDataSource.getMovieCredits(id = id)
        val movieVideo = movieRemoteDataSource.getMovieVideo(id = id)

        val result = movieInfo.mapCatching {
            it.copy(
                cast = movieCredits.getOrThrow(),
                video = movieVideo.getOrNull()
            )
        }

        return result
    }
}