package com.jik.core.network.interceptor


import com.jik.core.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor


internal val provideLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
}


internal val provideTmdbApiKeyInterceptor = Interceptor { chain ->
    val request = chain.request()
    val url = request.url.newBuilder()
        .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
        .build()
    chain.proceed(
        request.newBuilder()
            .url(url)
            .build()
    )
}