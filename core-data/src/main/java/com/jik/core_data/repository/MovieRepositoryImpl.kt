package com.jik.core_data.repository

import com.jik.core_model.Movie
import com.jik.core_model.MovieInfo
import com.jik.core_network.source.MovieRemoteDataSource

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override fun getPopularMovies(): List<Movie> {
        return movieRemoteDataSource.getPopularMovies()
    }

    override fun getMovieInfo(id: Int): MovieInfo {
        return movieRemoteDataSource.getMovieInfo(id = id)
    }
}