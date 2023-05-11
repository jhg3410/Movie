package com.jik.core.network.source

import com.jik.core.model.Movie
import com.jik.core.model.MovieInfo
import com.jik.core.network.service.MovieService

class MovieRemoteDataSourceImpl(
    private val movieService: MovieService
) : MovieRemoteDataSource {
    override suspend fun getPopularMovies(): Result<List<Movie>> {
        return movieService.getMovieList().mapCatching {
            it.results
        }
    }

    override suspend fun getMovieInfo(id: Int): Result<MovieInfo> {
        TODO("Not yet implemented")
    }
}