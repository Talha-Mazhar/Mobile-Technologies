package com.example.studyplanner

data class Task(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.MEDIUM,
    val dueDate: String = ""
)
enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}