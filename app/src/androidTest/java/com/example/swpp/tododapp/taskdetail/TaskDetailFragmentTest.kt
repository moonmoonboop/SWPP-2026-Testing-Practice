package com.example.swpp.tododapp.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.swpp.tododapp.R
import com.example.swpp.tododapp.ServiceLocator
import com.example.swpp.tododapp.data.Task
import com.example.swpp.tododapp.data.source.FakeAndroidTestRepository
import com.example.swpp.tododapp.data.source.TasksRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class TaskDetailFragmentTest {
    private lateinit var repository: TasksRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanupDb() = runTest { ServiceLocator.resetRepository() }

    @Test
    fun activeTaskDetails_DisplayedInUi() = runTest {
        // Given
        val activeTask = Task("Task Title", "Task Description", false, "details-task")
        repository.saveTask(activeTask)
        // When
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        val scenario = launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)
        try {
            // Then
            onView(withId(R.id.task_detail_title_text)).check(matches(isDisplayed()))
            onView(withId(R.id.task_detail_title_text)).check(matches(withText("Task Title")))
            onView(withId(R.id.task_detail_description_text)).check(matches(isDisplayed()))
            onView(withId(R.id.task_detail_description_text)).check(matches(withText("Task Description")))
            onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isDisplayed()))
            onView(withId(R.id.task_detail_complete_checkbox)).check(matches(not(isChecked())))
        } finally { scenario.close() }
    }
}
