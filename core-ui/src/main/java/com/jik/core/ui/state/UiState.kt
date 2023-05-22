package com.jik.core.ui.state

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

sealed interface UiState<out T> {
    object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val throwable: Throwable) : UiState<Nothing>
}

fun <T> getUiStateFlow(
    scope: CoroutineScope,
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
}.onStart { emit(UiState.Loading) }.flowOn(dispatcher).stateIn(
    scope = scope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = UiState.Loading
)