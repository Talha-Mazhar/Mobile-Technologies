package com.example.studenttasks

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studenttasks.ui.theme.StudentTasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentTasksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StudentTasksApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun StudentTasksApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var tasks by remember {
        mutableStateOf(
            listOf(
                Task(
                    id = 1,
                    title = "Prepare Kotlin exercise",
                    priority = Priority.HIGH
                ),
                Task(
                    id = 2,
                    title = "Read Android documentation",
                    priority = Priority.MEDIUM
                ),
                Task(
                    id = 3,
                    title = "Run the app",
                    isCompleted = true,
                    priority = Priority.LOW
                )
            )
        )
    }
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                tasks = tasks,
                onAddTask = {
                    navController.navigate("add")
                },
                onAbout = {
                    navController.navigate("about")
                },
                onCompletedChange = { changedTask, isCompleted ->
                    tasks = tasks.map { task ->
                        if (task.id == changedTask.id) {
                            task.copy(isCompleted = isCompleted)
                        } else {
                            task
                        }
                    }
                },
                onDelete = { taskToDelete ->
                    Log.d(
                        "StudentTasks",
                        "Deleting task ${taskToDelete.id}: ${taskToDelete.title}"
                    )
                    tasks = tasks.filter {
                        it.id != taskToDelete.id
                    }
                }
            )
        }

        composable("add") {
            AddTaskScreen(
                onSave = { title ->
                    val task = Task(
                        id = (tasks.maxOfOrNull { it.id } ?: 0) + 1,
                        title = title
                    )

                    tasks = tasks + task
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
        composable("about") {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Student Tasks",
                    style = MaterialTheme.typography.headlineLarge
                )

                Text(
                    "Introductory Kotlin/Android application"
                )

                Button(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Text("Back")
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    tasks: List<Task>,
    onAddTask: () -> Unit,
    onAbout: () -> Unit,
    onCompletedChange: (Task, Boolean) -> Unit,
    onDelete: (Task) -> Unit
) {
    val completedCount = tasks.count { it.isCompleted }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Student Tasks",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "$completedCount of ${tasks.size} completed"
        )

        Button(
            onClick = onAddTask
        ) {
            Text("Add Task")
        }
        Button(
            onClick = onAbout
        ) {
            Text("About")
        }
        if (tasks.isEmpty()) {
            Text("No tasks yet.")
        } else {
            TaskList(
                tasks = tasks,
                onCompletedChange = onCompletedChange,
                onDelete = onDelete,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun AddTaskScreen(
    onSave: (String) -> Unit,
    onCancel: () -> Unit
) {
    var title by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add Task",
            style = MaterialTheme.typography.headlineLarge
        )

        OutlinedTextField(
            value = title,
            onValueChange = { newValue ->
                title = newValue
            },
            label = {
                Text("Task title")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    onSave(title.trim())
                },
                enabled = title.isNotBlank()
            ) {
                Text("Save")
            }

            OutlinedButton(
                onClick = onCancel
            ) {
                Text("Cancel")
            }
        }
    }
}

@Composable
fun SimplePriorityDropdown(
    selectedOption: Priority,
    onOptionSelected: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text("Priority: $selectedOption")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Priority.entries.forEach { priority ->
                val isSelected = priority == selectedOption

                DropdownMenuItem(
                    text = {
                        Text(
                            text = if (isSelected) "✓ ${priority.name}" else "   ${priority.name}",
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    onClick = {
                        onOptionSelected(priority)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun TaskRow(
    task: Task,
    onCompletedChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = onCompletedChange
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = task.title,
                textDecoration = if (task.isCompleted) {
                    TextDecoration.LineThrough
                } else {
                    null
                }
            )

            Text(
                text = task.priority.name,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedButton(
            onClick = onDelete
        ) {
            Text("Delete")
        }
    }
}
@Composable
fun TaskList(
    tasks: List<Task>,
    onCompletedChange: (Task, Boolean) -> Unit,
    onDelete: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = tasks,
            key = { task -> task.id }
        ) { task ->
            TaskRow(
                task = task,
                onCompletedChange = { isCompleted ->
                    onCompletedChange(task, isCompleted)
                },
                onDelete = {
                    onDelete(task)
                }
            )
        }
    }
}


@Composable
fun CounterExample() {
    var count by remember { mutableStateOf(0) }

    Column {
        Text("Tasks: $count")

        Button(
            onClick = {
                count++
            }
        ) {
            Text("Add")
        }
    }
}


@Composable
fun AddTaskExample() {
    CounterExample()
    var taskTitle by rememberSaveable {
        mutableStateOf("")
    }
    var addAttempts by remember { mutableStateOf(0) }
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = taskTitle,
                onValueChange = { newValue ->
                    taskTitle = newValue
                },
                label = {
                    Text("Task title")
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    taskTitle = ""
                }
            ) {
                Text("Clear")
            }
        }
        Text("You typed: $taskTitle")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tasks added: $addAttempts")
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    addAttempts++
                },
                enabled = taskTitle.isNotBlank()
            ) {
                Text("Add Task")
            }
        }
    }
}





//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    StudentTasksTheme {
//        Greeting("Android")
//    }
//}