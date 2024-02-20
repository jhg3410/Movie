package com.jik.core.ui.state

sealed interface UiState<out T> {
    object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val throwable: Throwable) : UiState<Nothing>
}


fun <T> Result<T>.toUiState(): UiState<T> {
    return fold(
        onSuccess = {
            UiState.Success(it)
        },
        onFailure = {
            UiState.Error(it)
        }
    )
}