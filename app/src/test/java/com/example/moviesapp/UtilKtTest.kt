package com.example.moviesapp

import com.example.moviesapp.helper.calculateUserScorePercentage
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilKtTest {
    @Test
    fun testCalculateUserScorePercentage() {
        val testCases = listOf(
            0.0 to 0.0,
            5.0 to 50.0,
            7.5 to 75.0,
            10.0 to 100.0
        )

        for ((input, expected) in testCases) {
            val result = calculateUserScorePercentage(input)
            assertEquals(expected, result, 0.01) // delta parameter to allow a small difference in floating-point comparison
        }
    }
}
