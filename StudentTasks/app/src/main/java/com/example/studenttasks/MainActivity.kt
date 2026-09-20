package com.example.studenttasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

        Text(text = "Student Tasks", style = MaterialTheme.typography.headlineMedium)

        Text("3 Tasks")

        TaskRow(
            title = "Prepare Kotlin exercise",
            isCompleted = false,
            priority = Priority.MEDIUM,
        )

        TaskRow(
            title = "Install Android Studio",
            isCompleted = true,
            priority = Priority.LOW
        )

        Button(onClick = {}) {
            Text("Add Task")
        }


    }

}

@Composable
fun TaskRow(title: String, isCompleted: Boolean, priority: Priority) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = isCompleted, onCheckedChange = null)
        Text(text="${title}  ${priority}", modifier = Modifier.padding(start = 8.dp))

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