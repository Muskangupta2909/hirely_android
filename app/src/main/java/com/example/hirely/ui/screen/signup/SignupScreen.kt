package com.example.hirely.ui.screen.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hirely.viewmodel.SignupViewModel
import androidx.compose.runtime.setValue


@Composable
fun SignupScreen(
    onSignupSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: SignupViewModel = viewModel()
) {

    // ViewModel ki current state observe kar rahe hain
    val uiState by viewModel.uiState.collectAsState()

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    var isConfirmPasswordVisible by remember {
        mutableStateOf(false)
    }

    // Puri screen ka purple gradient background
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // colour
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 24.dp,
                vertical = 40.dp
            ),

        verticalArrangement = Arrangement.Center
    ) {

        // Heading
        Text(
            text = "Create Account",
            color = Color(0xFF243EAA),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        // Subtitle
        Text(
            text = "Sign up to get started",
            color = Color(0xFF6B7280),
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )


        // -----------------------------
        // Name
        // -----------------------------

        OutlinedTextField(
            value = uiState.name,

            onValueChange = {
                viewModel.onNameChange(it)
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Name")
            },

            placeholder = {
                Text("Enter your name")
            },

            singleLine = true,

            shape = RoundedCornerShape(10.dp),

            colors = hirelyTextFieldColors()
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )


        // -----------------------------
        // Email
        // -----------------------------

        OutlinedTextField(
            value = uiState.email,

            onValueChange = {
                viewModel.onEmailChange(it)
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Email")
            },

            placeholder = {
                Text("Enter your email")
            },

            singleLine = true,

            shape = RoundedCornerShape(10.dp),

            colors = hirelyTextFieldColors()
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )


        // -----------------------------
        // Phone
        // -----------------------------

        OutlinedTextField(
            value = uiState.phone,

            onValueChange = {
                viewModel.onPhoneChange(it)
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Phone")
            },

            placeholder = {
                Text("Enter your phone number")
            },

            singleLine = true,

            shape = RoundedCornerShape(10.dp),

            colors = hirelyTextFieldColors()
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )


        // -----------------------------
        // Password
        // -----------------------------

        OutlinedTextField(
            value = uiState.password,

            onValueChange = {
                viewModel.onPasswordChange(it)
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Password")
            },

            placeholder = {
                Text("Enter your password")
            },

            //for  hide show

            visualTransformation = if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },

            trailingIcon = {
                TextButton(
                    onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }
                ) {
                    Text(
                        text = if (isPasswordVisible) {
                            "Hide"
                        } else {
                            "Show"
                        },
                        color = Color(0xFF243EAA)
                    )
                }
            },




            singleLine = true,

            shape = RoundedCornerShape(10.dp),

            colors = hirelyTextFieldColors()
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )


        // -----------------------------
        // Confirm Password
        // -----------------------------

        OutlinedTextField(
            value = uiState.confirmPassword,

            onValueChange = {
                viewModel.onConfirmPasswordChange(it)
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Confirm Password")
            },

            placeholder = {
                Text("Confirm your password")
            },

            //show hide
            visualTransformation = if (isConfirmPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },

            trailingIcon = {
                TextButton(
                    onClick = {
                        isConfirmPasswordVisible =
                            !isConfirmPasswordVisible
                    }
                ) {
                    Text(
                        text = if (isConfirmPasswordVisible) {
                            "Hide"
                        } else {
                            "Show"
                        },
                        color = Color(0xFF243EAA)
                    )
                }
            },

            singleLine = true,

            shape = RoundedCornerShape(10.dp),

            colors = hirelyTextFieldColors()
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // -----------------------------
        // Sign Up Button
        // -----------------------------

        Button(
            onClick = {

                viewModel.signup(
                    onSuccess = onSignupSuccess
                )

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
                text = "Sign Up",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )


        // -----------------------------
        // Login
        // -----------------------------

        TextButton(
            onClick = {
                onLoginClick()
            },

            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Already have an account? ",
                color = Color(0xFF6B7280),
                fontSize = 13.sp
            )

            Text(
                text = "Login",
                color = Color(0xFF243EAA),
                fontSize = 13.sp
            )
        }


        // -----------------------------
        // Error
        // -----------------------------

        uiState.errorMessage?.let { error ->

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = error,
                color = Color(0xFFFF8A8A),
                fontSize = 13.sp
            )
        }
    }
}


// -----------------------------------------
// Hirely TextField Colors
// -----------------------------------------

@Composable
private fun hirelyTextFieldColors() =
    OutlinedTextFieldDefaults.colors(

        // User ka entered text
        focusedTextColor = Color(0xFF202124),
        unfocusedTextColor = Color(0xFF202124),

        // Label
        focusedLabelColor = Color(0xFF243EAA),
        unfocusedLabelColor = Color(0xFF6B7280),

        // Placeholder
        focusedPlaceholderColor = Color(0xFF6B7280),
        unfocusedPlaceholderColor = Color(0xFF6B7280),

        // Border
        focusedBorderColor = Color(0xFF243EAA),
        unfocusedBorderColor = Color(0xFFD9DCE5),

        // Cursor
        cursorColor = Color(0xFF243EAA),

        // Input background
        focusedContainerColor = Color(0xFFF7F8FC),
        unfocusedContainerColor = Color(0xFFF7F8FC)
    )