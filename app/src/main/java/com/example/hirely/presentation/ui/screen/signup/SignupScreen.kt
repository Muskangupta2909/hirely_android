package com.example.hirely.presentation.ui.screen.signup


import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hirely.presentation.viewmodel.SignupViewModel
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import com.example.hirely.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.res.painterResource

private val HirelyBlue = Color(0xFF243EAA)
private val HirelyGray = Color(0xFF6B7280)
private val HirelyBorder = Color(0xFFD9DCE5)
private val HirelyFieldBg = Color(0xFFF7F8FC)

@Composable
fun SignupScreen(
    onSignupSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit = {},
    viewModel: SignupViewModel = hiltViewModel()
) {

    // ViewModel ki current state observe kar rahe hain
    val uiState by viewModel.uiState.collectAsState()

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var agreedToTerms by remember { mutableStateOf(true) }

    val isEmailValid = android.util.Patterns.EMAIL_ADDRESS
        .matcher(uiState.email)
        .matches()

    val showEmailError = uiState.email.isNotEmpty() && !isEmailValid

    val isPhoneValid = uiState.phone.length == 10

    val showPhoneError = uiState.phone.isNotEmpty() && !isPhoneValid

    val isPasswordValid = uiState.password.length >= 8

    val showPasswordError = uiState.password.isNotEmpty() && !isPasswordValid

    val isConfirmPasswordValid = uiState.confirmPassword == uiState.password

    val showConfirmPasswordError =
        uiState.confirmPassword.isNotEmpty() && !isConfirmPasswordValid

    val isNameValid = uiState.name.isNotBlank()

    val showNameError = uiState.name.isNotEmpty() && !isNameValid

    val isFormValid =
        isNameValid &&
                isEmailValid &&
                isPhoneValid &&
                isPasswordValid &&
                isConfirmPasswordValid &&
                agreedToTerms

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current
    val emailFocusRequester = remember { FocusRequester() }
    val phoneFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize().imePadding()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp, vertical = 6.dp)
    ) {

        // -----------------------------
        // Back button
        // -----------------------------
        Box(
            modifier = Modifier.padding(top=30.dp)
                .size(32.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onBackClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            BackChevron()
        }

        Spacer(modifier = Modifier.height(2.dp))

        // -----------------------------
        // Logo
        // -----------------------------
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(62.dp)
                .background(HirelyBlue, RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "H",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Create Your Account",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = HirelyBlue,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Sign up to start finding your dream job",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = HirelyGray,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // -----------------------------
        // Full Name
        // -----------------------------
        Text(text = "Full Name", color = Color(0xFF202124), fontSize = 12.sp)
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = uiState.name,
            onValueChange = { viewModel.onNameChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            isError = showNameError,

            placeholder = { Text("Enter your full name", fontSize = 13.sp) },
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 13.sp),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = hirelyTextFieldColors()
        )
        if (showNameError) {
            Text(text = "Name is required", color = Color.Red, fontSize = 11.sp)
        }

        Spacer(modifier = Modifier.height(6.dp))

        // -----------------------------
        // Email
        // -----------------------------
        Text(text = "Email", color = Color(0xFF202124), fontSize = 12.sp)
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            isError = showEmailError,

            placeholder = { Text("Enter your email", fontSize = 13.sp) },
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 13.sp),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = hirelyTextFieldColors()
        )
        if (showEmailError) {
            Text(text = "Please enter a valid email", color = Color.Red, fontSize = 11.sp)
        }

        Spacer(modifier = Modifier.height(6.dp))

        // -----------------------------
        // Phone
        // -----------------------------
        Text(text = "Phone", color = Color(0xFF202124), fontSize = 12.sp)
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = uiState.phone,
            onValueChange = {
                if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                    viewModel.onPhoneChange(it)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            isError = showPhoneError,

            placeholder = { Text("Enter your phone number", fontSize = 13.sp) },
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 13.sp),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = hirelyTextFieldColors()
        )
        if (showPhoneError) {
            Text(text = "Phone number must be 10 digits", color = Color.Red, fontSize = 11.sp)
        }

        Spacer(modifier = Modifier.height(6.dp))

        // -----------------------------
        // Password
        // -----------------------------
        Text(text = "Password", color = Color(0xFF202124), fontSize = 12.sp)
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            isError = showPasswordError,

            placeholder = { Text("Enter your password", fontSize = 13.sp) },
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 13.sp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                TextButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Text(
                        text = if (isPasswordVisible) "Hide" else "Show",
                        color = HirelyBlue,
                        fontSize = 12.sp
                    )
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = hirelyTextFieldColors()
        )
        if (showPasswordError) {
            Text(text = "Password must be at least 8 characters", color = Color.Red, fontSize = 11.sp)
        }

        Spacer(modifier = Modifier.height(6.dp))

        // -----------------------------
        // Confirm Password
        // -----------------------------
        Text(text = "Confirm Password", color = Color(0xFF202124), fontSize = 12.sp)
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = uiState.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)

                //ime
            .focusRequester(confirmPasswordFocusRequester),

            isError = showConfirmPasswordError,

            placeholder = { Text("Confirm your password", fontSize = 13.sp) },
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 13.sp),
            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                TextButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                    Text(
                        text = if (isConfirmPasswordVisible) "Hide" else "Show",
                        color = HirelyBlue,
                        fontSize = 12.sp
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

            shape = RoundedCornerShape(10.dp),
            colors = hirelyTextFieldColors()
        )
        if (showConfirmPasswordError) {
            Text(text = "Passwords do not match", color = Color.Red, fontSize = 11.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // -----------------------------
        // Terms & Conditions checkbox (single line)
        // -----------------------------
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = agreedToTerms,
                onCheckedChange = { agreedToTerms = it },
                colors = CheckboxDefaults.colors(checkedColor = HirelyBlue),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Row {
                Text(text = "I agree to the ", color = Color(0xFF202124), fontSize = 11.sp)
                Text(
                    text = "Terms & Conditions",
                    color = HirelyBlue,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { /* TODO: open terms */ }
                )
                Text(text = " and ", color = Color(0xFF202124), fontSize = 11.sp)
                Text(
                    text = "Privacy Policy",
                    color = HirelyBlue,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { /* TODO: open privacy policy */ }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // -----------------------------
        // Sign Up Button
        // -----------------------------
        Button(
            onClick = {
                if (isFormValid) {
                    viewModel.signup(onSuccess = onSignupSuccess)
                } else {
                    val message = if (!agreedToTerms) {
                        "Please agree to the Terms & Conditions"
                    } else {
                        "All fields are required"
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            shape = RoundedCornerShape(23.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = HirelyBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Sign Up", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // -----------------------------
        // OR SIGN UP WITH divider
        // -----------------------------
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = HirelyBorder)
            Text(
                text = "  OR SIGN UP WITH  ",
                color = HirelyGray,
                fontSize = 10.sp
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = HirelyBorder)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // -----------------------------
        // Social buttons
        // -----------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = { /* TODO: Google sign up */ },
                modifier = Modifier
                    .weight(1f)
                    .height(42.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "Google",
                    modifier = Modifier.size(15.dp)
                )
            }

            OutlinedButton(
                onClick = { /* TODO: LinkedIn sign up */ },
                modifier = Modifier
                    .weight(1f)
                    .height(42.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.linkedin),
                    contentDescription = "LinkedIn",
                    modifier = Modifier.size(15.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // -----------------------------
        // Login
        // -----------------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLoginClick() },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Already have an account? ", color = Color(0xFF202124), fontSize = 12.sp)
            Text(text = "Login", color = HirelyBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        // -----------------------------
        // Error
        // -----------------------------
        uiState.errorMessage?.let { error ->
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = error, color = Color(0xFFFF8A8A), fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(6.dp))
    }
}

// -----------------------------------------
// Hirely TextField Colors
// -----------------------------------------

@Composable
private fun hirelyTextFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color(0xFF202124),
        unfocusedTextColor = Color(0xFF202124),

        focusedLabelColor = HirelyBlue,
        unfocusedLabelColor = HirelyGray,

        focusedPlaceholderColor = HirelyGray,
        unfocusedPlaceholderColor = HirelyGray,

        focusedBorderColor = HirelyBlue,
        unfocusedBorderColor = HirelyBorder,

        errorTextColor = Color.Red,
        errorBorderColor = Color.Red,
        errorLabelColor = Color.Red,
        errorCursorColor = Color.Red,

        cursorColor = HirelyBlue,

        focusedContainerColor = HirelyFieldBg,
        unfocusedContainerColor = HirelyFieldBg
    )

// -----------------------------------------
// Back Chevron Icon
// -----------------------------------------

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