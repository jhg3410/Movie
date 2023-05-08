package com.jik.core.data.repository

import com.jik.core.model.Movie
import com.jik.core.model.MovieInfo
import com.jik.core.network.source.MovieRemoteDataSource

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override fun getPopularMovies(): Result<List<Movie>> {
        return movieRemoteDataSource.getPopularMovies()
    }

    override fun getMovieInfo(id: Int): Result<MovieInfo> {
        return movieRemoteDataSource.getMovieInfo(id = id)
    }
}