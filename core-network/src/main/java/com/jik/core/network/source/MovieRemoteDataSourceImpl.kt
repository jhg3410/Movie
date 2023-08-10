package com.jik.core.network.source

import com.jik.core.model.Movie
import com.jik.core.model.MovieInfo
import com.jik.core.network.service.MovieService
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRemoteDataSource {

    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return movieService.getPopularMovieList(page).mapCatching {
            it.results
        }
    }

    override suspend fun getMovieInfo(id: Long): Result<MovieInfo> {
        return movieService.getMovieInfo(id)
    }
}