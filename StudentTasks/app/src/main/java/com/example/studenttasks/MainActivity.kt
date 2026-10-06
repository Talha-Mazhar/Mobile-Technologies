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
import androidx.compose.ui.unit.dp
import com.example.studenttasks.ui.theme.StudentTasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentTasksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    StudentTasksApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

//Composable mean this has something to show

@Composable
fun StudentTasksApp(modifier: Modifier = Modifier) {



    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Text(text = "Student Tasks", style = MaterialTheme.typography.headlineLarge)

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TaskRow(
                title = "Prepare Kotlin exercise",
                isCompleted = false,
                priority = Priority.HIGH
            )

            TaskRow(
                title = "Read Android documentation",
                isCompleted = false,
                priority = Priority.MEDIUM
            )

            TaskRow(
                title = "Run the app",
                isCompleted = true,
                priority = Priority.LOW
            )
        }


        AddTaskExample()


    }

}



@Composable
fun TaskRow(title: String, isCompleted: Boolean, priority: Priority) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = isCompleted, onCheckedChange = null)
        Text(text="${title}  ${priority}", modifier = Modifier.padding(start = 8.dp))
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