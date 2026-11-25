package com.example.hirely.presentation.ui.screen.profile

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hirely.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {

    // Actual logged-in user's details
    val user by viewModel.loggedInUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                horizontal = 24.dp,
                vertical = 40.dp
            ),

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Profile",
            color = Color(0xFF243EAA),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        // Name
        Text(
            text = "Name",
            color = Color(0xFF6B7280),
            fontSize = 13.sp
        )

        Text(
            text = user?.name ?: "Loading...",
            color = Color(0xFF202124),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // Email
        Text(
            text = "Email",
            color = Color(0xFF6B7280),
            fontSize = 13.sp
        )

        Text(
            text = user?.email ?: "Loading...",
            color = Color(0xFF202124),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // Phone
        Text(
            text = "Phone",
            color = Color(0xFF6B7280),
            fontSize = 13.sp
        )

        Text(
            text = user?.phone ?: "Loading...",
            color = Color(0xFF202124),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        // Logout
        Button(
            onClick = {
                onLogoutClick()
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),

            shape = RoundedCornerShape(10.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF243EAA),
                contentColor = Color.White
            )
        ) {

            Text(
                text = "Logout",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}