package com.jik.core.network.service

import com.jik.core.network.model.MovieResponse
import retrofit2.http.GET


interface MovieService {

    @GET("/movie/popular")
    suspend fun getMovieList(): Result<MovieResponse>
}