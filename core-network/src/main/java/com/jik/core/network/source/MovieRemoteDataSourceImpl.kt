package com.jik.core.network.source

import com.jik.core.model.Movie
import com.jik.core.model.MovieInfo

class MovieRemoteDataSourceImpl : MovieRemoteDataSource {
    override fun getPopularMovies(): Result<List<Movie>> {
        TODO("Not yet implemented")
    }

    override fun getMovieInfo(id: Int): Result<MovieInfo> {
        TODO("Not yet implemented")
    }
}