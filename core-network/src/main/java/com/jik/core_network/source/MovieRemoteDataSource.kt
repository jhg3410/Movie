package com.jik.core_network.source

interface MovieRemoteDataSource {

    fun getPopularMovies()

    fun getMovieInfo(id: Int)
}