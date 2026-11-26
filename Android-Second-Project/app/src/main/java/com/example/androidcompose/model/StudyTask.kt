package com.example.androidcompose.model

data class StudyTask(
    val id: Int,
    val title: String,
    val completed: Boolean = false,
)

object StudyTaskState {
    fun initialTasks(): List<StudyTask> = listOf(
        StudyTask(id = 1, title = "学习 Column 和 Row", completed = true),
        StudyTask(id = 2, title = "学习状态管理"),
        StudyTask(id = 3, title = "完成 Compose 实验"),
    )

    fun addTask(tasks: List<StudyTask>, title: String): List<StudyTask> {
        val cleanTitle = title.trim()
        if (cleanTitle.isEmpty()) return tasks

        val nextId = (tasks.maxOfOrNull(StudyTask::id) ?: 0) + 1
        return tasks + StudyTask(id = nextId, title = cleanTitle)
    }

    fun toggleTask(tasks: List<StudyTask>, id: Int): List<StudyTask> =
        tasks.map { task ->
            if (task.id == id) task.copy(completed = !task.completed) else task
        }

    fun deleteTask(tasks: List<StudyTask>, id: Int): List<StudyTask> =
        tasks.filterNot { it.id == id }

    fun completedCount(tasks: List<StudyTask>): Int = tasks.count(StudyTask::completed)
}

