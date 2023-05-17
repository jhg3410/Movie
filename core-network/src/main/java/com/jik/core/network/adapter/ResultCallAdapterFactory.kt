package com.jik.core.network.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        fun create() = ResultCallAdapterFactory()
    }


    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        val wrapperType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (getRawType(wrapperType) != Result::class.java) {
            return null
        }

        val resultType = getParameterUpperBound(0, wrapperType as ParameterizedType)

        return ResultCallAdapter(resultType)
    }
}
