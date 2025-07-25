package com.pidygb.mynasadailypics.core.common

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ResultTest {

    @Test
    fun `asResult emits Loading then Success for successful flow`() = runTest {
        val flow = flow { emit("data") }
        val results = flow.asResult().toList()

        assertEquals(2, results.size)
        assertIs<Result.Loading>(results[0])
        assertIs<Result.Success<String>>(results[1])
        assertEquals("data", (results[1] as Result.Success<String>).data)
    }

    @Test
    fun `asResult emits Loading then Error for failing flow`() = runTest {
        val exception = RuntimeException("Test exception")
        val flow = flow<String> { throw exception }
        val results = flow.asResult().toList()

        assertEquals(2, results.size)
        assertIs<Result.Loading>(results[0])
        assertIs<Result.Error>(results[1])
        assertEquals(exception, (results[1] as Result.Error).exception)
    }

    @Test
    fun `asResult emits Loading then completes for empty flow`() = runTest {
        val flow = flow<String> {}
        val results = flow.asResult().toList()

        assertEquals(1, results.size)
        assertIs<Result.Loading>(results[0])
    }
}
