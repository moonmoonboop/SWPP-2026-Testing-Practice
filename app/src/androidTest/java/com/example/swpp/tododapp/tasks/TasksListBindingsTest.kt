package com.example.swpp.tododapp.tasks

import android.view.View
import android.widget.TextView
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.swpp.tododapp.data.Task
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksListBindingsTest {
    private fun textView() = TextView(InstrumentationRegistry.getInstrumentation().targetContext)
    private fun onMain(block: () -> Unit) {
        var failure: Throwable? = null
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            try { block() } catch (error: Throwable) { failure = error }
        }
        failure?.let { throw it }
    }

    @Test
    fun setDueDateLabel_dueToday_updatesExpectedText() = onMain {
        // Given
        val today = LocalDate.now()
        val view = textView().apply { visibility = View.GONE }
        val task = Task(title = "Task", id = "task-1", dueDate = today.plusDays(0L))
        // When
        setDueDateLabel(view, task)
        // Then
        assertEquals("D-Day", view.text.toString())
        assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun setDueDateLabel_dueInThreeDays_updatesExpectedText() = onMain {
        // Given
        val today = LocalDate.now()
        val view = textView().apply { visibility = View.GONE }
        val task = Task(title = "Task", id = "task-1", dueDate = today.plusDays(3L))
        // When
        setDueDateLabel(view, task)
        // Then
        assertEquals("D-3", view.text.toString())
        assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun setDueDateLabel_overdueByTwoDays_updatesExpectedText() = onMain {
        // Given
        val today = LocalDate.now()
        val view = textView().apply { visibility = View.GONE }
        val task = Task(title = "Task", id = "task-1", dueDate = today.plusDays(-2L))
        // When
        setDueDateLabel(view, task)
        // Then
        assertEquals("D+2", view.text.toString())
        assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun setDueDateLabel_dueYesterday_updatesExpectedText() = onMain {
        // Given
        val today = LocalDate.now()
        val view = textView().apply { visibility = View.GONE }
        val task = Task(title = "Task", id = "task-1", dueDate = today.plusDays(-1L))
        // When
        setDueDateLabel(view, task)
        // Then
        assertEquals("D+1", view.text.toString())
        assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun setDueDateLabel_dueTomorrow_updatesExpectedText() = onMain {
        // Given
        val today = LocalDate.now()
        val view = textView().apply { visibility = View.GONE }
        val task = Task(title = "Task", id = "task-1", dueDate = today.plusDays(1L))
        // When
        setDueDateLabel(view, task)
        // Then
        assertEquals("D-1", view.text.toString())
        assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun setDueDateLabel_completedWithoutDueDate_updatesExpectedText() = onMain {
        // Given
        val view = textView().apply { visibility = View.GONE }
        val task = Task(title = "Task", id = "task-1", isCompleted = true, dueDate = null)
        // When
        setDueDateLabel(view, task)
        // Then
        assertEquals("CLEAR", view.text.toString())
        assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun setDueDateLabel_completedWithPastDueDate_updatesExpectedText() = onMain {
        // Given
        val view = textView().apply { visibility = View.GONE }
        val task = Task(title = "Task", id = "task-1", isCompleted = true, dueDate = LocalDate.now().plusDays(-2L))
        // When
        setDueDateLabel(view, task)
        // Then
        assertEquals("CLEAR", view.text.toString())
        assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun setDueDateLabel_completedWithTodayDueDate_updatesExpectedText() = onMain {
        // Given
        val view = textView().apply { visibility = View.GONE }
        val task = Task(title = "Task", id = "task-1", isCompleted = true, dueDate = LocalDate.now().plusDays(0L))
        // When
        setDueDateLabel(view, task)
        // Then
        assertEquals("CLEAR", view.text.toString())
        assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun setDueDateLabel_completedWithFutureDueDate_updatesExpectedText() = onMain {
        // Given
        val view = textView().apply { visibility = View.GONE }
        val task = Task(title = "Task", id = "task-1", isCompleted = true, dueDate = LocalDate.now().plusDays(3L))
        // When
        setDueDateLabel(view, task)
        // Then
        assertEquals("CLEAR", view.text.toString())
        assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun setDueDateLabel_activeWithoutDueDate_updatesExpectedText() = onMain {
        // Given
        val view = textView()
        val task = Task(title = "Task", id = "task-1", dueDate = null)
        // When
        setDueDateLabel(view, task)
        // Then
        assertEquals("", view.text.toString())
    }

    @Test
    fun setDueDateLabel_activeWithoutDueDateClearsOldLabel_updatesExpectedText() = onMain {
        // Given
        val view = textView().apply { text = "CLEAR"; visibility = View.VISIBLE }
        val task = Task(title = "Task", id = "task-1", dueDate = null)
        // When
        setDueDateLabel(view, task)
        // Then
        assertEquals("", view.text.toString())
    }

    @Test
    fun setDueDateLabel_nullTaskClearsOldLabel_updatesExpectedText() = onMain {
        // Given
        val view = textView().apply { text = "D-3"; visibility = View.VISIBLE }
        // When
        setDueDateLabel(view, null)
        // Then
        assertEquals("", view.text.toString())
    }

    @Test
    fun setDueDateLabel_completedToActiveWithoutDueDate_updatesExpectedText() = onMain {
        // Given
        val view = textView()
        val completed = Task(title = "Task", id = "task-1", isCompleted = true, dueDate = null)
        setDueDateLabel(view, completed)
        assertEquals("CLEAR", view.text.toString())
        // When: simulate unchecking completion on the same task and rebinding.
        setDueDateLabel(view, completed.copy(isCompleted = false))
        // Then
        assertEquals("", view.text.toString())
    }

    @Test
    fun setDueDateLabel_activeToCompletedWithoutDueDate_updatesExpectedText() = onMain {
        // Given
        val view = textView()
        val active = Task(title = "Task", id = "task-1", dueDate = null)
        setDueDateLabel(view, active)
        assertEquals("", view.text.toString())
        // When
        setDueDateLabel(view, active.copy(isCompleted = true))
        // Then
        assertEquals("CLEAR", view.text.toString())
        assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun setDueDateLabel_completedToActiveWithDueDate_updatesExpectedText() = onMain {
        // Given
        val view = textView()
        val completed = Task(title = "Task", id = "task-1", isCompleted = true, dueDate = LocalDate.now().plusDays(3))
        setDueDateLabel(view, completed)
        assertEquals("CLEAR", view.text.toString())
        // When
        setDueDateLabel(view, completed.copy(isCompleted = false))
        // Then
        assertEquals("D-3", view.text.toString())
        assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun setDueDateLabel_reusedRowWithoutDueDate_updatesExpectedText() = onMain {
        // Given
        val view = textView()
        setDueDateLabel(view, Task(title = "First", id = "task-1", dueDate = LocalDate.now().plusDays(3)))
        assertEquals("D-3", view.text.toString())
        // When: a recycled view receives a different task.
        setDueDateLabel(view, Task(title = "Second", id = "task-2", dueDate = null))
        // Then
        assertEquals("", view.text.toString())
    }

}
