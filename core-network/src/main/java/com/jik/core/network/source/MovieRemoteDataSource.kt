package com.jik.core.network.source

import com.jik.core.model.Movie
import com.jik.core.model.MovieInfo

interface MovieRemoteDataSource {

    fun getPopularMovies(): Result<List<Movie>>

    fun getMovieInfo(id: Int): Result<MovieInfo>
}