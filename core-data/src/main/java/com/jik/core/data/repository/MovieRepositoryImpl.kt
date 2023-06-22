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

    override suspend fun getMovieInfo(id: Int): Result<MovieInfo> {
        return movieRemoteDataSource.getMovieInfo(id = id)
    }
}