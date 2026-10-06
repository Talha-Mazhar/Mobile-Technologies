package com.example.studenttasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
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
    val sampleTasks = listOf(
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

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Student Tasks", style = MaterialTheme.typography.headlineLarge)
        TaskList(sampleTasks)
        Text("${sampleTasks.size} tasks")
        val completedCount = sampleTasks.count { it.isCompleted }
        Text(
            "$completedCount of ${sampleTasks.size} completed"
        )
        AddTaskExample()
    }
}
@Composable
fun TaskRow(task: Task) {

    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = task.isCompleted, onCheckedChange = null)
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(text = task.title, textDecoration = if(task.isCompleted){
                TextDecoration.LineThrough
            }else {
                null
            })
            Text(
                text = task.priority.name,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
@Composable
fun TaskList(
    tasks: List<Task>
) {

    if(tasks.isEmpty()){
        println("No tasks yet.")
        return
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = tasks,
            key = { task -> task.id }
        ) { task ->
            TaskRow(task)
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