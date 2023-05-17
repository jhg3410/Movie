package com.jik.common.ui

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.System.currentTimeMillis
import kotlin.time.Duration.Companion.seconds

class UiStateTest {

    @Test
    fun success() = runTest {
        val success: suspend () -> Result<String> = suspend {
            Result.success(mockData)
        }
        val uiStateFlowOfSuccess = getUiStateFlow {
            success()
        }
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
    fun fail() = runTest {
        val failure: suspend () -> Result<String> = suspend {
            Result.failure(mockThrowable)
        }
        val uiStateFlowOfFail = getUiStateFlow {
            failure()
        }
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


    @Test
    fun loadingTime() = runTest {
        val delayTime = 2.seconds
        var startTime = 0L
        var loadingTime = 0L

        val uiStateFlowOfSuccess = getUiStateFlow() {
            delay(delayTime)
            Result.success(mockData)
        }

        uiStateFlowOfSuccess.collectLatest {
            when (it) {
                is UiState.Loading -> {
                    startTime = currentTimeMillis()
                }
                is UiState.Success -> {
                    loadingTime = currentTimeMillis() - startTime
                }
                is UiState.Error -> Unit
            }
        }
        println("loadingTime: $loadingTime")
    }
}


private const val mockData = "mockData"
private val mockThrowable = Throwable("error")