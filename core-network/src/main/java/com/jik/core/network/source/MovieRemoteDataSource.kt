package com.jik.core.network.source

import com.jik.core.model.Movie
import com.jik.core.model.MovieInfo

interface MovieRemoteDataSource {

    suspend fun getPopularMovies(page: Int): Result<List<Movie>>

    suspend fun getMovieInfo(id: Long): Result<MovieInfo>

    suspend fun getMovieCredits(id: Long): Result<List<MovieInfo.CastItem>>

    suspend fun getMovieVideo(id: Long): Result<MovieInfo.VideoInfo>
}