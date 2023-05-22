package com.jik.core.ui

import com.jik.core.ui.state.UiState
import com.jik.core.ui.state.getUiStateFlow
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class UiStateTest {

    private val result = mutableListOf<UiState<String>>()
    private val successData = "success"
    private val failData = Throwable("fail")

    @Before
    fun setUp() {
        result.clear()
    }

    @Test
    fun success() = runTest {

        launch {
            val uiStateFlow = getUiStateFlow(scope = this, block = { Result.success(successData) })

            uiStateFlow.collect { uiState ->
                result.add(uiState)

                if (result.size == 2) {
                    println(result)
                    assertEquals(listOf(UiState.Loading, UiState.Success(successData)), result)
                    cancel()
                }
            }
        }
    }

    @Test
    fun fail() = runTest {

        launch {
            val uiStateFlow =
                getUiStateFlow(scope = this, block = { Result.failure<String>(failData) })

            uiStateFlow.collect { uiState ->
                result.add(uiState)

                if (result.size == 2) {
                    assertEquals(listOf(UiState.Loading, UiState.Error(failData)), result)
                    cancel()
                }
            }
        }
    }

    @Test
    fun loading() = runTest {

        val delayTime = 2.seconds
        var startTime = 0L
        var loadingTime = 0L

        launch {
            val uiStateFlow = getUiStateFlow(scope = this, block = {
                delay(delayTime)
                Result.success(successData)
            })

            uiStateFlow.collect { uiState ->
                if (uiState is UiState.Loading) {
                    startTime = System.currentTimeMillis()
                } else {
                    loadingTime = System.currentTimeMillis() - startTime
                }
                result.add(uiState)

                if (result.size == 2) {
                    println(loadingTime.milliseconds)
                    cancel()
                }
            }
        }
    }
}
