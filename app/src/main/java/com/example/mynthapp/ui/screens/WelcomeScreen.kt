package com.example.mynthapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mynthapp.ui.theme.ObsidianAccent
import com.example.mynthapp.ui.theme.ObsidianBackground
import com.example.mynthapp.ui.theme.ObsidianText
import com.example.mynthapp.R

@Composable
fun WelcomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBackground),
        color = ObsidianBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.mynth_logo),
                contentDescription = "Mynth Logo",
                modifier = Modifier
                    .size(240.dp)
                    .padding(bottom = 24.dp)
            )
            Text(
                text = "Welcome to Mynth",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = ObsidianAccent
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Let’s ground your mind and set your first intention.",
                fontSize = 16.sp,
                color = ObsidianText
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("moodSelector") },
                colors = ButtonDefaults.buttonColors(backgroundColor = ObsidianAccent),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .shadow(4.dp, RoundedCornerShape(12.dp))
            ) {
                Text(
                    text = "Begin",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
