package eu.tutorials.myattendanceapp

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.tutorials.myattendanceapp.ui.theme.MyAttendanceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel : AuthViewModel by viewModels()
        //val todoViewModel : TodoViewModel by viewModels()
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        //for saving generated qrcode in picture folder
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }

        setContent {
            MyAttendanceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    MyAppNavigation(modifier = Modifier.fillMaxSize(),
                        authViewModel = authViewModel,
                        todoViewModel = todoViewModel)
                }
            }
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun LoginMenu() {
    MyAttendanceAppTheme {
        LoginMenu("Android")
    }
}*/