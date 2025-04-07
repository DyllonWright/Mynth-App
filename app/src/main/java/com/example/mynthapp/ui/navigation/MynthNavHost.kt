package com.example.mynthapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mynthapp.ui.screens.*

@Composable
fun MynthNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("welcome") { WelcomeScreen(navController) }
        composable("moodSelector") { MoodSelectorScreen(navController) }
        composable("intention") { IntentionScreen(navController) }
        composable("focusTimer") { FocusTimerScreen(navController) }
        composable("reflection") { ReflectionScreen(navController) }
    }
}
