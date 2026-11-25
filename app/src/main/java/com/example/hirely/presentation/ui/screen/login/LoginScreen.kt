package com.example.hirely.presentation.ui.screen.login


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hirely.presentation.viewmodel.LoginViewModel

private val HirelyBlue = Color(0xFF243EAA)
private val HirelyGray = Color(0xFF6B7280)
private val HirelyBorder = Color(0xFFD9DCE5)
private val HirelyFieldBg = Color(0xFFF7F8FC)

// ---------------------------------------------------------
// Hirely Login Screen
// ---------------------------------------------------------

@Composable
fun LoginScreen(

    // Login successful hone ke baad call hoga
    onLoginSuccess: (String) -> Unit,
    onSignupClick: () -> Unit,
    onBackClick: () -> Unit = {},

    // LoginViewModel ko screen ke saath connect kar rahe hain
    viewModel: LoginViewModel = hiltViewModel()
) {

    // ViewModel ki current UI state observe kar rahe hain
    val uiState by viewModel.uiState.collectAsState()

    // Password show/hide karne ki temporary UI state
    var isPasswordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val passwordFocusRequester = remember { FocusRequester() }

    // -----------------------------------------------------
    // Main Background
    // -----------------------------------------------------

    Column(
        modifier = Modifier
            .fillMaxSize().imePadding()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        // -----------------------------
        // Back button
        // -----------------------------
        Box(
            modifier = Modifier
                .size(36.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onBackClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            BackChevron()
        }

        Spacer(modifier = Modifier.height(12.dp))

        // -----------------------------
        // Logo
        // -----------------------------
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(64.dp)
                .background(HirelyBlue, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "H",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Hirely",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color(0xFF111111),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Welcome Back!",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color(0xFF111111),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Login to continue",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = HirelyGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        // -------------------------------------------------
        // Email Field
        // -------------------------------------------------

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Text(text = "✉️", fontSize = 16.sp)
            },
            placeholder = { Text("Enter your email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { passwordFocusRequester.requestFocus() }
            ),
            shape = RoundedCornerShape(12.dp),
            colors = hirelyLoginTextFieldColors()
        )

        Spacer(modifier = Modifier.height(14.dp))

        // -------------------------------------------------
        // Password Field
        // -------------------------------------------------

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester),
            leadingIcon = {
                Text(text = "🔒", fontSize = 16.sp)
            },
            placeholder = { Text("Enter your password") },
            visualTransformation = if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                TextButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Text(
                        text = if (isPasswordVisible) "Hide" else "Show",
                        color = HirelyBlue
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            shape = RoundedCornerShape(12.dp),
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
                onClick = { /* Forgot password later add */ }
            ) {
                Text(
                    text = "Forgot Password?",
                    color = HirelyBlue,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // -------------------------------------------------
        // Login Button
        // -------------------------------------------------

        Button(
            onClick = {
                viewModel.login(
                    onLoginSuccess = { role ->
                        onLoginSuccess(role)
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = !uiState.isLoading,
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = HirelyBlue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = if (uiState.isLoading) "Logging in..." else "Login",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // -------------------------------------------------
        // OR CONTINUE WITH divider
        // -------------------------------------------------

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = HirelyBorder)
            Text(
                text = "  or continue with  ",
                color = HirelyGray,
                fontSize = 12.sp
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = HirelyBorder)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // -------------------------------------------------
        // Social buttons
        // -------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { /* TODO: Google login */ },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "G", color = Color(0xFF4285F4), fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = { /* TODO: LinkedIn login */ },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "in", color = Color(0xFF0A66C2), fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // -------------------------------------------------
        // Signup Navigation
        // -------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",
                color = Color(0xFF202124),
                fontSize = 13.sp
            )
            Text(
                text = "Sign Up",
                color = HirelyBlue,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onSignupClick() }
            )
        }

        // -------------------------------------------------
        // Error Message
        // -------------------------------------------------

        uiState.errorMessage?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color(0xFFFF8A8A),
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
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

        // Placeholder
        focusedPlaceholderColor = HirelyGray,
        unfocusedPlaceholderColor = HirelyGray,

        // Border
        focusedBorderColor = HirelyBlue,
        unfocusedBorderColor = HirelyBorder,

        // Cursor
        cursorColor = HirelyBlue,

        // Input background
        focusedContainerColor = HirelyFieldBg,
        unfocusedContainerColor = HirelyFieldBg
    )

// ---------------------------------------------------------
// Back Chevron Icon
// ---------------------------------------------------------

@Composable
private fun BackChevron(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(22.dp)) {
        val strokeWidth = 2.5.dp.toPx()
        val path = Path().apply {
            moveTo(size.width * 0.62f, size.height * 0.2f)
            lineTo(size.width * 0.32f, size.height * 0.5f)
            lineTo(size.width * 0.62f, size.height * 0.8f)
        }
        drawPath(
            path = path,
            color = Color(0xFF202124),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}