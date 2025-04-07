package com.example.mynthapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.mynthapp.ui.navigation.MynthNavHost

@Composable
fun MynthApp() {
    val navController = rememberNavController()
    MynthNavHost(navController = navController)
}
