package com.jik.core.network.source

import com.jik.core.model.Movie
import com.jik.core.model.MovieInfo

interface MovieRemoteDataSource {

    suspend fun getPopularMovies(): Result<List<Movie>>

    suspend fun getMovieInfo(id: Int): Result<MovieInfo>
}