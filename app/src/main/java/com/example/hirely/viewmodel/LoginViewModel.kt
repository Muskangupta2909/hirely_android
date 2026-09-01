package com.example.hirely.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hirely.data.DataStoreManager
import com.example.hirely.ui.screen.login.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Login screen ka business logic yahan rahega
class LoginViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Internal mutable state
    // Sirf ViewModel isko modify karega
    private val _uiState = MutableStateFlow(LoginUiState())

    // UI ko read-only state provide karenge
    val uiState: StateFlow<LoginUiState> =
        _uiState.asStateFlow()

    // DataStore ko handle karne ke liye manager
    private val dataStoreManager =
        DataStoreManager(getApplication())


    // ---------------------------------------------------------
    // Email / Phone
    // ---------------------------------------------------------

    // Email ya phone change hone par state update karega
    fun onEmailChange(email: String) {

        _uiState.value = _uiState.value.copy(
            email = email,
            errorMessage = null
        )
    }


    // ---------------------------------------------------------
    // Password
    // ---------------------------------------------------------

    // Password change hone par state update karega
    fun onPasswordChange(password: String) {

        _uiState.value = _uiState.value.copy(
            password = password,
            errorMessage = null
        )
    }


    // ---------------------------------------------------------
    // Login
    // ---------------------------------------------------------

    // Login button click hone par call hoga
    fun login(
        onLoginSuccess: () -> Unit
    ) {

        // Current login state
        val currentState = _uiState.value

        // Email required
        if (currentState.email.trim().isEmpty()) {

            _uiState.value = currentState.copy(
                errorMessage = "Email is required"
            )

            return
        }

        // Password required
        if (currentState.password.isEmpty()) {

            _uiState.value = currentState.copy(
                errorMessage = "Password is required"
            )

            return
        }

        // Email format validation
        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(currentState.email.trim())
                .matches()
        ) {

            _uiState.value = currentState.copy(
                errorMessage = "Please enter a valid email address"
            )

            return
        }

        // Loading start
        _uiState.value = currentState.copy(
            isLoading = true,
            errorMessage = null
        )

        // Coroutine start
        viewModelScope.launch {

            // DataStore se saare registered users read kar rahe hain
            val users = dataStoreManager.users.first()

            // Email ya Phone se matching user find kar rahe hain
            val user = users.find { registeredUser ->

                registeredUser.email.equals(
                    currentState.email,
                    ignoreCase = true
                ) || registeredUser.phone == currentState.email
            }

            // -------------------------------------------------
            // User nahi mila
            // -------------------------------------------------

            if (user == null) {

                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage =
                        "Invalid email or password"
                )

                return@launch
            }


            // -------------------------------------------------
            // Password check
            // -------------------------------------------------

            if (user.password != currentState.password) {

                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage =
                        "Invalid email/phone or password"
                )

                return@launch
            }


            // -------------------------------------------------
            // User valid hai
            // -------------------------------------------------

            // Current logged-in user ka email save karenge
            dataStoreManager.saveLoginData(
                email = user.email
            )

            // Loading stop
            _uiState.value = currentState.copy(
                isLoading = false,
                errorMessage = null
            )

            // Login successful
            onLoginSuccess()
        }
    }
}