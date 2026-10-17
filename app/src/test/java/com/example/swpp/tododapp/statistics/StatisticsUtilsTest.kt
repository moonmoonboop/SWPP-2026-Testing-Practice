package com.example.swpp.tododapp.statistics

import com.example.swpp.tododapp.data.Task
import org.junit.Assert.assertEquals
import org.junit.Test

class StatisticsUtilsTest {
    @Test
    fun getActiveAndCompletedStats_two_return50f50f() {
        // Given: one active and one completed task.
        val tasks = listOf(Task("Completed", "description", true, "1"), Task("Active", "description", false, "2"))
        // When
        val result = getActiveAndCompletedStats(tasks)
        // Then
        assertEquals(50f, result.activeTasksPercent)
        assertEquals(50f, result.completedTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_zero_return0f0f() {
        // Given
        val tasks = emptyList<Task>()
        // When
        val result = getActiveAndCompletedStats(tasks)
        // Then
        assertEquals(0f, result.activeTasksPercent)
        assertEquals(0f, result.completedTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_twoCompleted_return0f100f() {
        // Given
        val tasks = listOf(Task("First", "description", true, "1"), Task("Second", "description", true, "2"))
        // When
        val result = getActiveAndCompletedStats(tasks)
        // Then
        assertEquals(0f, result.activeTasksPercent)
        assertEquals(100f, result.completedTasksPercent)
    }
}
