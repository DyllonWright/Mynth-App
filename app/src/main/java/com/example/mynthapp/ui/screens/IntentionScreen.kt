package com.example.mynthapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun IntentionScreen(navController: NavController) {
    // Simulate an AI-generated intention message
    val intentionMessage = "Today, you hold the Mirror."

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Your Intention Message:")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = intentionMessage)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("focusTimer") }) {
            Text(text = "Start Focus Session")
        }
    }
}
