package com.jik.common.ui

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

sealed interface UiState<out T> {
    object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val throwable: Throwable) : UiState<Nothing>
}

fun <T> getUiStateFlow(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> Result<T>
) = flow {
    val result = block()
    result.onSuccess {
        emit(UiState.Success(it))
    }
    result.onFailure {
        emit(UiState.Error(it))
    }
}.onStart { emit(UiState.Loading) }.flowOn(dispatcher)