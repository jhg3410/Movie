package com.jik.core.network.source

import com.jik.core.model.Movie
import com.jik.core.model.MovieInfo

interface MovieRemoteDataSource {

    fun getPopularMovies(): List<Movie>

    fun getMovieInfo(id: Int): MovieInfo
}