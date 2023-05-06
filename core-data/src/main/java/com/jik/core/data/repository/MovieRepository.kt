package com.jik.core.data.repository

import com.jik.core.model.Movie
import com.jik.core.model.MovieInfo

interface MovieRepository {

    fun getPopularMovies(): List<Movie>

    fun getMovieInfo(id: Int): MovieInfo
}