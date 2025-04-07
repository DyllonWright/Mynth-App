package com.example.mynthapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun FocusTimerScreen(navController: NavController) {
    // Example: 10-minute timer
    var timeLeft by remember { mutableStateOf(10 * 60) }  // 10 min in seconds
    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (isRunning && timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
        if (timeLeft == 0) {
            navController.navigate("reflection")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Focus Timer: $timeLeft seconds left")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // End session early
            isRunning = false
            navController.navigate("reflection")
        }) {
            Text(text = "End Session")
        }
    }
}
