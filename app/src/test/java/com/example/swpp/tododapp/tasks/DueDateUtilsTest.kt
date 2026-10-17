package com.example.swpp.tododapp.tasks

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class DueDateUtilsTest {
    @Test
    fun dueDateLabel_completedWithoutDueDate_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = null
        // When
        val result = dueDateLabel(dueDate, today, true)
        // Then
        assertEquals("CLEAR", result)
    }

    @Test
    fun dueDateLabel_completedWithPastDueDate_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-10-15")
        // When
        val result = dueDateLabel(dueDate, today, true)
        // Then
        assertEquals("CLEAR", result)
    }

    @Test
    fun dueDateLabel_completedWithTodayDueDate_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-10-17")
        // When
        val result = dueDateLabel(dueDate, today, true)
        // Then
        assertEquals("CLEAR", result)
    }

    @Test
    fun dueDateLabel_completedWithFutureDueDate_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-10-20")
        // When
        val result = dueDateLabel(dueDate, today, true)
        // Then
        assertEquals("CLEAR", result)
    }

    @Test
    fun dueDateLabel_activeWithoutDueDate_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = null
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("", result)
    }

    @Test
    fun dueDateLabel_dueToday_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-10-17")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-Day", result)
    }

    @Test
    fun dueDateLabel_dueInThreeDays_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-10-20")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-3", result)
    }

    @Test
    fun dueDateLabel_overdueByTwoDays_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-10-15")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D+2", result)
    }

    @Test
    fun dueDateLabel_dueYesterday_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-10-16")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D+1", result)
    }

    @Test
    fun dueDateLabel_dueTomorrow_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-10-18")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_futureBeforeMonthAnniversary_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-11-16")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-30", result)
    }

    @Test
    fun dueDateLabel_futureOnMonthAnniversary_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-11-17")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-31", result)
    }

    @Test
    fun dueDateLabel_futureAfterMonthAnniversary_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-11-18")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-32", result)
    }

    @Test
    fun dueDateLabel_pastBeforeMonthAnniversary_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-11-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-10-16")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D+32", result)
    }

    @Test
    fun dueDateLabel_pastOnMonthAnniversary_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-11-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-10-17")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D+31", result)
    }

    @Test
    fun dueDateLabel_pastAfterMonthAnniversary_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-11-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-10-18")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D+30", result)
    }

    @Test
    fun dueDateLabel_beforeMonthEnd_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-01-30")
        val dueDate: LocalDate? = LocalDate.parse("2026-01-30")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-Day", result)
    }

    @Test
    fun dueDateLabel_onMonthEnd_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-01-30")
        val dueDate: LocalDate? = LocalDate.parse("2026-01-31")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_afterMonthEnd_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-01-30")
        val dueDate: LocalDate? = LocalDate.parse("2026-02-01")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-2", result)
    }

    @Test
    fun dueDateLabel_beforeYearEnd_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-12-30")
        val dueDate: LocalDate? = LocalDate.parse("2026-12-30")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-Day", result)
    }

    @Test
    fun dueDateLabel_onYearEnd_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-12-30")
        val dueDate: LocalDate? = LocalDate.parse("2026-12-31")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_afterYearEnd_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-12-30")
        val dueDate: LocalDate? = LocalDate.parse("2027-01-01")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-2", result)
    }

    @Test
    fun dueDateLabel_beforeLeapDay_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2024-02-28")
        val dueDate: LocalDate? = LocalDate.parse("2024-02-28")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-Day", result)
    }

    @Test
    fun dueDateLabel_onLeapDay_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2024-02-28")
        val dueDate: LocalDate? = LocalDate.parse("2024-02-29")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_afterLeapDay_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2024-02-28")
        val dueDate: LocalDate? = LocalDate.parse("2024-03-01")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-2", result)
    }

    @Test
    fun dueDateLabel_overdueAcrossLeapDay_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2024-03-01")
        val dueDate: LocalDate? = LocalDate.parse("2024-02-28")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D+2", result)
    }

    @Test
    fun dueDateLabel_overdueOnLeapDay_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2024-03-01")
        val dueDate: LocalDate? = LocalDate.parse("2024-02-29")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D+1", result)
    }

    @Test
    fun dueDateLabel_nonLeapFebruary_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2025-02-28")
        val dueDate: LocalDate? = LocalDate.parse("2025-03-01")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_futureWholeYear_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2026-10-17")
        val dueDate: LocalDate? = LocalDate.parse("2027-10-17")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D-365", result)
    }

    @Test
    fun dueDateLabel_pastWholeYear_returnsExpectedLabel() {
        // Given: exact dates from the reviewed spec, with a literal expected label.
        val today = LocalDate.parse("2027-10-17")
        val dueDate: LocalDate? = LocalDate.parse("2026-10-17")
        // When
        val result = dueDateLabel(dueDate, today, false)
        // Then
        assertEquals("D+365", result)
    }

}
