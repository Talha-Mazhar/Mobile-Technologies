package com.example.imagelocationdemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
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

        LocationPermissionExample()
    }
}

@Composable
fun LocationPermissionExample() {
    val context = LocalContext.current
    var hasPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
        if (granted) {
            Toast.makeText(
                context,
                context.getString(R.string.location_granted),
                Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context,
                context.getString(R.string.location_denied),
                Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = if (hasPermission) stringResource(R.string.location_granted) else stringResource(R.string.location_denied))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val isGranted = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                if (isGranted) {
                    hasPermission = true
                } else {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        ) {
            Text("Enable location")
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
