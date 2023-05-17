package com.jik.core.network.adapter

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

internal class ResultCallAdapter(
    private val responseType: Type
) : CallAdapter<Type, Call<Result<Type>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<Type>): Call<Result<Type>> = ResultCall(call)
}


private class ResultCall<T>(
    private val proxy: Call<T>
) : Call<Result<T>> {

    override fun enqueue(callback: Callback<Result<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onResponse(this@ResultCall, Response.success(response.toResult()))
            }

            private fun Response<T>.toResult(): Result<T> {
                val body = body()

                if (isSuccessful && body != null) {
                    return Result.success(body)
                }

                return if (body == null) {
                    Result.failure(Throwable("Body is null"))
                } else {
                    Result.failure(Throwable(errorBody()?.string() ?: "Unknown error"))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(this@ResultCall, Response.success(Result.failure(t)))
            }
        })
    }


    override fun clone(): Call<Result<T>> = ResultCall(proxy.clone())

    override fun execute(): Response<Result<T>> = throw UnsupportedOperationException()

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun cancel() = proxy.cancel()

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()
}