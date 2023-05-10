package com.jik.common.ui

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class UiStateTest {

    @Test
    fun successResult() = runBlocking {

        val successResponse = Result.success(mockData)
        val uiStateFlowOfSuccess = successResponse.toUiStateFlow()

        val successResult = mutableListOf<UiState<String>>()


        uiStateFlowOfSuccess.collectLatest {
            when (it) {
                is UiState.Loading -> successResult.add(it)
                is UiState.Success -> successResult.add(it)
                is UiState.Error -> successResult.add(it)
            }
        }


        assertEquals(successResult.component1(), UiState.Loading)
        assertEquals(successResult.component2(), UiState.Success(mockData))
    }


    @Test
    fun failResult() = runBlocking {

        val failResponse = Result.failure<String>(mockThrowable)
        val uiStateFlowOfFail = failResponse.toUiStateFlow()

        val failResult = mutableListOf<UiState<String>>()


        uiStateFlowOfFail.collectLatest {
            when (it) {
                is UiState.Loading -> failResult.add(it)
                is UiState.Success -> failResult.add(it)
                is UiState.Error -> failResult.add(it)
            }
        }

        assertEquals(failResult.component1(), UiState.Loading)
        assertEquals(failResult.component2(), UiState.Error(mockThrowable))
    }
}


private const val mockData = "mockData"
private val mockThrowable = Throwable("error")