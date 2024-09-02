package com.jik.core.network.source

import com.jik.core.model.Movie
import com.jik.core.model.MovieInfo
import com.jik.core.network.service.MovieService
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRemoteDataSource {

    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return movieService.getPopularMovieList(page).map {
            it.results
        }
    }

    override suspend fun getMovieInfo(id: Long): Result<MovieInfo> {
        return movieService.getMovieInfo(id)
    }

    override suspend fun getMovieCredits(id: Long): Result<List<MovieInfo.CastItem>> {
        return movieService.getMovieCredits(id).map {
            it.cast.filter { castItem ->
                castItem.knownForDepartment == "Acting"
            }
        }
    }

    override suspend fun getMovieVideo(id: Long): Result<MovieInfo.VideoInfo> {
        return movieService.getMovieVideo(id).mapCatching { response ->
            response.results.find {
                it.site == "YouTube" && it.type == "Teaser" && it.official
            } ?: response.results.find {
                it.site == "YouTube"
            } ?: throw Exception("No video found")
        }
    }
}