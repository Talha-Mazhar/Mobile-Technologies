package com.example.imagelocationdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.imagelocationdemo.ui.theme.ImageLocationDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageLocationDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ScreenWithCard()
                    }
                }
            }
        }
    }
}

@Composable
fun ResourceExample() {
    Image(
        painter = painterResource(id = R.drawable.geolocation),
        contentDescription = "Geolocation map",
    )
}

@Composable
fun PermissionCard() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = stringResource(R.string.welcome_title))
            Text(text = stringResource(R.string.welcome_description))
        }
    }
}

@Composable
fun ScreenWithCard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.geolocation),
            contentDescription = "Map preview"
        )

        Text(text = stringResource(R.string.app_name))
        PermissionCard()

        Button(onClick = { /* action */ }) {
            Text(stringResource(R.string.permission_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenWithCardPreview() {
    ImageLocationDemoTheme {
        ScreenWithCard()
    }
}
