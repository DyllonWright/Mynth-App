package com.example.mynthapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ReflectionScreen(navController: NavController) {
    var reflection by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Reflect on your session in one line:")
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = reflection,
            onValueChange = { reflection = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Save reflection if desired, then navigate back or to a "home" screen
            navController.navigate("welcome")
        }) {
            Text(text = "Done")
        }
    }
}
