package com.example.studenttasks

enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}

fun priorityLabel(priority: Priority): String {
    return when (priority) {
        Priority.LOW -> "Low"
        Priority.MEDIUM -> "Medium"
        Priority.HIGH -> "High"
    }
}

fun describeTask(task: Task): String {
    return "${task.title} - ${task.priority}"
}

data class Task (
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.MEDIUM,

)

