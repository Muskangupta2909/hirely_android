package com.example.hirely.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hirely.viewmodel.LoginViewModel


// ---------------------------------------------------------
// Hirely Login Screen
// ---------------------------------------------------------

@Composable
fun LoginScreen(

    // Login successful hone ke baad call hoga

    onLoginSuccess: () -> Unit,
    onSignupClick: () -> Unit,

    // LoginViewModel ko screen ke saath connect kar rahe hain
    viewModel: LoginViewModel = viewModel()
) {

    // ViewModel ki current UI state observe kar rahe hain
    val uiState by viewModel.uiState.collectAsState()

    // Password show/hide karne ki temporary UI state
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }


    // -----------------------------------------------------
    // Main Background
    // -----------------------------------------------------

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {


    // -------------------------------------------------
        // Login Card
        // -------------------------------------------------

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 24.dp,
                    vertical = 40.dp
                ),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            // -------------------------------------------------
            // Heading
            // -------------------------------------------------

            Text(
                text = "Welcome Back!",
                color = Color(0xFF243EAA),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Login to your account",
                color = Color(0xFF6B7280),
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // -------------------------------------------------
            // Email Field
            // -------------------------------------------------

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

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),

                shape = RoundedCornerShape(10.dp),

                colors = hirelyLoginTextFieldColors()
            )


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // -------------------------------------------------
            // Password Field
            // -------------------------------------------------

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

                // Password show/hide
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },

                // Show / Hide button
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

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),

                shape = RoundedCornerShape(10.dp),

                colors = hirelyLoginTextFieldColors()
            )


            // -------------------------------------------------
            // Forgot Password
            // -------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.End
            ) {

                TextButton(
                    onClick = {
                        // Forgot password later add
                    }
                ) {
                    Text(
                        text = "Forgot Password?",
                        color = Color(0xFF243EAA),
                        fontSize = 13.sp
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            // -------------------------------------------------
            // Login Button
            // -------------------------------------------------

            Button(
                    onClick = {

                        viewModel.login(
                            onLoginSuccess = onLoginSuccess
                        )
                    },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),

                enabled = !uiState.isLoading,

                shape = RoundedCornerShape(10.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF243EAA),
                    contentColor = Color.White
                )
            ) {

                Text(
                    text = if (uiState.isLoading) {
                        "Logging in..."
                    } else {
                        "Login"
                    },

                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            // -------------------------------------------------
            // Signup Navigation
            // -------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.Center,

                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Don't have an account?",
                    color = Color(0xFF6B7280),
                    fontSize = 13.sp
                )

                TextButton(
                    onClick = {
                        onSignupClick()

                    }
                ) {
                    Text(
                        text = "Sign Up",
                        color = Color(0xFF243EAA),
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            // -------------------------------------------------
            // Error Message
            // -------------------------------------------------

            uiState.errorMessage?.let { error ->

                Text(
                    text = error,
                    color = Color(0xFFFF8A8A),
                    fontSize = 13.sp
                )
            }
        }
    }
}


// ---------------------------------------------------------
// Login TextField Colors
// ---------------------------------------------------------

@Composable
private fun hirelyLoginTextFieldColors() =
    OutlinedTextFieldDefaults.colors(

        // Entered text
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