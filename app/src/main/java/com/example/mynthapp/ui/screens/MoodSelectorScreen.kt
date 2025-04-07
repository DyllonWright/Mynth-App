package com.example.mynthapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MoodSelectorScreen(navController: NavController) {
    var selectedMood by remember { mutableStateOf("Neutral") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "How are you feeling today?")
        Spacer(modifier = Modifier.height(8.dp))

        // Example buttons to pick moods:
        Button(onClick = { selectedMood = "Sad" }) { Text("Sad") }
        Button(onClick = { selectedMood = "Focused" }) { Text("Focused") }
        Button(onClick = { selectedMood = "Tired" }) { Text("Tired") }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Potentially pass `selectedMood` along if needed
            navController.navigate("intention")
        }) {
            Text(text = "Next")
        }
    }
}
