package com.jik.feature.popular

import androidx.lifecycle.ViewModel
import com.jik.core.model.Movie

class PopularViewModel : ViewModel() {

    val popularMock: List<Movie> = listOf(
        Movie(
            id = 502356,
            title = "The Super Mario Bros. Movie",
            posterPath = "/qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg"
        ),
        Movie(
            id = 677179,
            title = "Creed III",
            posterPath = "/vJU3rXSP9hwUuLeq8IpfsJShLOk.jpg"
        ),
        Movie(
            id = 76600,
            title = "Avatar: The Way of Water",
            posterPath = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg"
        ),
        Movie(
            id = 638974,
            title = "Murder Mystery 2",
            posterPath = "/wdffZv8gIiWy6xr4t7hWBWtUwpl.jpg"
        ),
        Movie(
            id = 638974,
            title = "Murder Mystery 2",
            posterPath = "/wdffZv8gIiWy6xr4t7hWBWtUwpl.jpg"
        ),
        Movie(
            id = 638974,
            title = "Murder Mystery 2",
            posterPath = "/wdffZv8gIiWy6xr4t7hWBWtUwpl.jpg"
        ),
    )
}