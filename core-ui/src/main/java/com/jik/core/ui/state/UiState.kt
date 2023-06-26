package com.jik.core.ui.state

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


fun <T> Result<T>.toUiState(): UiState<T> {
    onSuccess {
        return UiState.Success(it)
    }.onFailure {
        return UiState.Error(it)
    }
    return UiState.Loading
}

fun <T> getUiStateFlow(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> Result<T>,
) = flow {
    emit(block().toUiState())
}.onStart { emit(UiState.Loading) }.flowOn(dispatcher)