package com.jik.core_data.repository

import com.jik.core_network.source.MovieRemoteDataSource

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override fun getPopularMovies() {
        movieRemoteDataSource.getPopularMovies()
    }

    override fun getMovieInfo(id: Int) {
        movieRemoteDataSource.getMovieInfo(id = id)
    }
}