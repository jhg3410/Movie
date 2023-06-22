package com.jik.core.data.repository

import com.jik.core.model.Movie
import com.jik.core.model.MovieInfo

interface MovieRepository {

    suspend fun getPopularMovies(page: Int): Result<List<Movie>>

    suspend fun getMovieInfo(id: Int): Result<MovieInfo>
}