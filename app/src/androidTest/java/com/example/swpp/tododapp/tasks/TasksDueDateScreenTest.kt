package com.example.swpp.tododapp.tasks

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.swpp.tododapp.R
import com.example.swpp.tododapp.ServiceLocator
import com.example.swpp.tododapp.data.Task
import com.example.swpp.tododapp.data.source.FakeAndroidTestRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class TasksDueDateScreenTest {
    private lateinit var repository: FakeAndroidTestRepository

    @Before
    fun setupRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanupRepository() = runTest { ServiceLocator.resetRepository() }

    @Test
    fun setDueDateLabel_checkThenUncheckNoDateTask_removesClear() = runTest {
        // Given: a single active task without a due date in the real task-list UI.
        repository.saveTask(Task("No due date", "Screen regression", false, "screen-task", null))
        val scenario = launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)
        try {
            onView(withId(R.id.complete_checkbox)).check(matches(not(isChecked())))
            // When: complete it and then make it active again.
            onView(withId(R.id.complete_checkbox)).perform(click())
            onView(withId(R.id.due_date_text)).check(matches(withText("CLEAR")))
            onView(withId(R.id.complete_checkbox)).perform(click())
            // Then: CLEAR must disappear; the empty text is checked independently of visibility.
            onView(withId(R.id.complete_checkbox)).check(matches(not(isChecked())))
            onView(withId(R.id.due_date_text)).check(matches(withText("")))
        } finally { scenario.close() }
    }
}
