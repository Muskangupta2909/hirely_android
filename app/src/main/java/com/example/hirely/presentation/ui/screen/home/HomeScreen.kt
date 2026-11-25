package com.example.hirely.presentation.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    userName: String,
    role: String,

    // Create Job click
    onCreateJobClick: () -> Unit,

    // Job List click
    onJobListClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                horizontal = 24.dp,
                vertical = 40.dp
            ),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ---------------------------------------------
        // Welcome Heading
        // ---------------------------------------------

        Text(
            text = "Welcome, $userName 👋",
            color = Color(0xFF243EAA),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // ---------------------------------------------
        // Subtitle
        // ---------------------------------------------

        Text(
            text = if (role == "ADMIN") {
                "Manage jobs from here"
            } else {
                "Find your next job"
            },
            color = Color(0xFF6B7280),
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        // ---------------------------------------------
        // ADMIN ONLY
        // ---------------------------------------------

        if (role == "ADMIN") {

            Button(
                onClick = onCreateJobClick,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),

                shape = RoundedCornerShape(10.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF243EAA),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Create Job",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }

        // ---------------------------------------------
        // JOB LIST
        // USER + ADMIN
        // ---------------------------------------------

        Button(
            onClick = onJobListClick,

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            shape = RoundedCornerShape(10.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF243EAA),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Job List",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}