package com.jik.core.network.service

import com.jik.core.model.MovieInfo
import com.jik.core.network.model.CreditsResponse
import com.jik.core.network.model.MovieResponse
import com.jik.core.network.model.VideosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovieList(
        @Query("page") page: Int
    ): Result<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieInfo(
        @Path("movie_id") id: Long
    ): Result<MovieInfo>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") id: Long
    ): Result<CreditsResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id") id: Long
    ): Result<VideosResponse>
}