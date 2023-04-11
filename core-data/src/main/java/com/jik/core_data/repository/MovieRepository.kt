package com.jik.core_data.repository

import com.jik.core_model.Movie
import com.jik.core_model.MovieInfo

interface MovieRepository {

    fun getPopularMovies():List<Movie>

    fun getMovieInfo(id: Int): MovieInfo
}