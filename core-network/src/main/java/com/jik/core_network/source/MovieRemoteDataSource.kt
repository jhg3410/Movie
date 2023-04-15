package com.jik.core_network.source

import com.jik.core_model.Movie
import com.jik.core_model.MovieInfo

interface MovieRemoteDataSource {

    fun getPopularMovies(): List<Movie>

    fun getMovieInfo(id: Int): MovieInfo
}