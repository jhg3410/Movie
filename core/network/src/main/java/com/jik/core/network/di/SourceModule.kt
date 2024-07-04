package com.jik.core.network.di

import com.jik.core.network.source.MovieRemoteDataSource
import com.jik.core.network.source.MovieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SourceModule {

    @Binds
    fun bindMovieRemoteDataSource(
        movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl
    ): MovieRemoteDataSource
}