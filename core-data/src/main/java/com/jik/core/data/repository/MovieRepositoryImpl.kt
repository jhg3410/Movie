package com.jik.core.data.repository

import com.jik.core.model.Movie
import com.jik.core.model.MovieInfo
import com.jik.core.network.source.MovieRemoteDataSource
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return movieRemoteDataSource.getPopularMovies(page)
    }

    override suspend fun getMovieInfo(id: Long): Result<MovieInfo> {
        val movieInfo = movieRemoteDataSource.getMovieInfo(id = id)
        val movieCredits = movieRemoteDataSource.getMovieCredits(id = id)

        val result = movieInfo.fold(
            onSuccess = { movieInfoData ->
                movieCredits.fold(
                    onSuccess = { movieCreditsData ->
                        Result.success(movieInfoData.copy(cast = movieCreditsData))
                    },
                    onFailure = { movieCreditsException ->
                        Result.failure(movieCreditsException)
                    }
                )
            },
            onFailure = { movieInfoException ->
                Result.failure(movieInfoException)
            }
        )

        return result
    }
}