package com.example.androidcompose.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StudyTaskStateTest {
    @Test
    fun initialState_hasThreeTasksAndOneCompleted() {
        val tasks = StudyTaskState.initialTasks()

        assertEquals(3, tasks.size)
        assertEquals(1, StudyTaskState.completedCount(tasks))
    }

    @Test
    fun addTask_appendsTrimmedTaskAndKeepsCompletionCount() {
        val tasks = StudyTaskState.addTask(
            tasks = StudyTaskState.initialTasks(),
            title = "  复习 LazyColumn  ",
        )

        assertEquals(4, tasks.size)
        assertEquals("复习 LazyColumn", tasks.last().title)
        assertEquals(1, StudyTaskState.completedCount(tasks))
    }

    @Test
    fun toggleTask_updatesCountAndCompletionState() {
        val tasks = StudyTaskState.toggleTask(
            tasks = StudyTaskState.initialTasks(),
            id = 2,
        )

        assertTrue(tasks.first { it.id == 2 }.completed)
        assertEquals(2, StudyTaskState.completedCount(tasks))
    }

    @Test
    fun deleteTask_removesTask() {
        val tasks = StudyTaskState.deleteTask(
            tasks = StudyTaskState.initialTasks(),
            id = 1,
        )

        assertFalse(tasks.any { it.id == 1 })
        assertEquals(2, tasks.size)
        assertEquals(0, StudyTaskState.completedCount(tasks))
    }
}

