package eu.tutorials.myattendanceapp

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AutoRefreshScreen() {
    var counter by remember { mutableStateOf(0) }
    val handler = remember { Handler(Looper.getMainLooper()) }

    DisposableEffect(Unit) {
        val runnable = object : Runnable {
            override fun run() {
                counter++
                handler.postDelayed(this, 1000) // Refresh every second
            }
        }
        handler.post(runnable)
        onDispose {
            handler.removeCallbacks(runnable)
        }
    }

    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Counter: $counter", fontSize = 24.sp)
    }*/
}

/*
 * 1. CustomButton is a composable function that takes a content
 * parameter of type @Composable RowScope.() -> Unit.
 * 2. Inside CustomButton, the Button composable is used,
 * and the content parameter is passed to a Row composable.
 * This approach allows you to create more flexible and reusable UI components by leveraging the RowScope within the Button content.
 *
 * EX implementation: CustomButton { RefreshButtonScreen() }
 *
 */
@Composable
fun CustomButton(content: @Composable () -> Unit) {
    Button(onClick = { /* Do something */ }) {
        Text(text = "Refresh")
        content()
    }
}

@Composable
fun RefreshButtonScreen() {
    val scope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    var data by remember { mutableStateOf("Initial Data") }

    //fun refreshData() {
        scope.launch {
            refreshing = true
            delay(1000) // Simulate a network request
            data = "Refreshed Data"
            refreshing = false
        }
    //}

    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = data)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { refreshData() },
            enabled = !refreshing
        ) {
            Text(text = if (refreshing) "Refreshing..." else "Refresh")
        }
    }*/


}