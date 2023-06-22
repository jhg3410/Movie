package com.jik.core.network.service

import com.jik.core.network.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovieList(
        @Query("page") page: Int
    ): Result<MovieResponse>
}