package com.jik.core_data.repository

import com.jik.core_model.Movie
import com.jik.core_model.MovieInfo

class MovieRepositoryMock() : MovieRepository {

    override fun getPopularMovies(): List<Movie> {
        return listOf(
            Movie(
                id = 502356,
                title = "The Super Mario Bros. Movie",
                poserPath = "/qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg"
            ),
            Movie(
                id = 677179,
                title = "Creed III",
                poserPath = "/vJU3rXSP9hwUuLeq8IpfsJShLOk.jpg"
            ),
            Movie(
                id = 76600,
                title = "Avatar: The Way of Water",
                poserPath = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg"
            ),
            Movie(
                id = 638974,
                title = "Murder Mystery 2",
                poserPath = "/wdffZv8gIiWy6xr4t7hWBWtUwpl.jpg"
            ),
        )
    }

    override fun getMovieInfo(id: Int): MovieInfo =
        MovieInfo(
            id = 76600,
            title = "Avatar: The Way of Water",
            genres = listOf(
                MovieInfo.Genre(
                    id = 878,
                    name = "Science Fiction"
                ),
                MovieInfo.Genre(
                    id = 12,
                    name = "Adventure"
                ),
                MovieInfo.Genre(
                    id = 28,
                    name = "Action"
                )
            ),
            overview = "",
            posterPath = "",
            rating = 0.0
        )
}