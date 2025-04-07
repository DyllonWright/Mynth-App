package com.example.mynthapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mynthapp.ui.MynthApp
import com.example.mynthapp.ui.theme.MynthAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MynthAppTheme {
                MynthApp()
            }
        }
    }
}
