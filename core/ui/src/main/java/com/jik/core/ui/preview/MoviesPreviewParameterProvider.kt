package com.jik.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.jik.core.model.MovieInfo

class MovieInfoPreviewParameterProvider : PreviewParameterProvider<MovieInfo> {

    override val values: Sequence<MovieInfo> = sequenceOf(PreviewParameterData.movieInfos)
}

private object PreviewParameterData {

    val movieInfos =
        MovieInfo(
            id = 346698,
            title = "Barbie",
            overview = "Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land. However, when they get a chance to go to the real world, they soon discover the joys and perils of living among humans.",
            posterPath = "/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg",
            backdropPath = "/ctMserH8g2SeOAnCw5gFjdQF8mo.jpg",
            rating = 7.004,
            releaseDate = "2023-07-19",
            genres = listOf(
                MovieInfo.Genre("Animation"),
                MovieInfo.Genre("Family"),
                MovieInfo.Genre("Adventure")
            ),
            cast = listOf(
                MovieInfo.CastItem(
                    name = "Margot Robbie",
                    character = "Barbie",
                    profilePath = "/lyCBy56im2St8sJXDAFogfrt3XF.jpg",
                    knownForDepartment = "Acting"
                ),
                MovieInfo.CastItem(
                    name = "Ryan Gosling",
                    character = "Ken",
                    profilePath = "/lyCBy56im2St8sJXDAFogfrt3XF.jpg",
                    knownForDepartment = "Acting"
                ),
                MovieInfo.CastItem(
                    name = "Emma Stone",
                    character = "Skipper",
                    profilePath = "/lyCBy56im2St8sJXDAFogfrt3XF.jpg",
                    knownForDepartment = "Acting"
                ),
            )
        )
}