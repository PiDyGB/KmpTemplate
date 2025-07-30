@file:OptIn(ExperimentalCoroutinesApi::class)

package com.pidygb.template.core.common

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.time.Duration.Companion.seconds

class ResultTest {

    @Test
    fun `asResult emits Loading then Success for successful flow`() = runTest {
        val testScope = TestScope(StandardTestDispatcher(testScheduler))
        val flow = flow { emit("data") }

        flow.asResult<String>(testScope).test(timeout = 3.seconds) {
            // The first emission should be Loading
            assertIs<Result.Loading>(awaitItem())

            // The second emission should be Success
            val successItem = awaitItem()
            assertIs<Result.Success<String>>(successItem)
            assertEquals("data", successItem.data)

            // No more emissions expected
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `asResult emits Loading then Error for failing flow`() = runTest {
        val testScope = TestScope(StandardTestDispatcher(testScheduler))
        val exception = RuntimeException("Test exception")
        val flow = flow<String> { throw exception }

        flow.asResult<String>(testScope).test(timeout = 3.seconds) {
            // The first emission should be Loading
            assertIs<Result.Loading>(awaitItem())

            // The second emission should be Error
            val errorItem = awaitItem()
            assertIs<Result.Error>(errorItem)
            assertEquals(exception, errorItem.exception)

            // No more emissions expected
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `asResult emits Loading for empty flow`() = runTest {
        val testScope = TestScope(StandardTestDispatcher(testScheduler))
        val flow = flow<String> {}

        flow.asResult<String>(testScope).test(timeout = 3.seconds) {
            // Only Loading should be emitted for an empty flow
            assertIs<Result.Loading>(awaitItem())

            // No more emissions expected
            cancelAndIgnoreRemainingEvents()
        }
    }
}