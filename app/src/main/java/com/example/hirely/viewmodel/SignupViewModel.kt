package com.example.hirely.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hirely.data.DataStoreManager
import com.example.hirely.data.model.User
import com.example.hirely.ui.screen.signup.SignupUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Signup screen ka business logic yahan rahega
class SignupViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Internal mutable state
    // Sirf ViewModel isko modify karega
    private val _uiState = MutableStateFlow(SignupUiState())

    // UI ko read-only state provide karenge
    val uiState: StateFlow<SignupUiState> =
        _uiState.asStateFlow()

    // DataStore ko handle karne ke liye manager
    private val dataStoreManager =
        DataStoreManager(getApplication())


    // ---------------------------------------------------------
    // Name
    // ---------------------------------------------------------

    // User ka name change hone par state update karega
    fun onNameChange(name: String) {

        _uiState.value = _uiState.value.copy(
            name = name
        )
    }


    // ---------------------------------------------------------
    // Email
    // ---------------------------------------------------------

    // User ka email change hone par state update karega
    fun onEmailChange(email: String) {

        _uiState.value = _uiState.value.copy(
            email = email
        )
    }


    // ---------------------------------------------------------
    // Phone
    // ---------------------------------------------------------

    // User ka phone number change hone par state update karega
    fun onPhoneChange(phone: String) {

        _uiState.value = _uiState.value.copy(
            phone = phone
        )
    }


    // ---------------------------------------------------------
    // Password
    // ---------------------------------------------------------

    // User ka password change hone par state update karega
    fun onPasswordChange(password: String) {

        _uiState.value = _uiState.value.copy(
            password = password
        )
    }


    // ---------------------------------------------------------
    // Confirm Password
    // ---------------------------------------------------------

    // Confirm password change hone par state update karega
    fun onConfirmPasswordChange(confirmPassword: String) {

        _uiState.value = _uiState.value.copy(
            confirmPassword = confirmPassword
        )
    }


    // ---------------------------------------------------------
    // Signup
    // ---------------------------------------------------------

    // Signup button click hone par call hoga
    fun signup(
        onSuccess: () -> Unit
    ) {

        // Confirm karne ke liye Logcat mein message dekhenge
        Log.d(
            "HIRELY_SIGNUP",
            "SIGN UP BUTTON CLICKED"
        )

        // Current UI state
        val currentState = _uiState.value

        // -----------------------------------------------------
        // 1. Check all fields
        // -----------------------------------------------------

        if (
            currentState.name.trim().isEmpty() ||
            currentState.email.trim().isEmpty() ||
            currentState.phone.trim().isEmpty() ||
            currentState.password.isEmpty() ||
            currentState.confirmPassword.isEmpty()
        ) {

            _uiState.value = currentState.copy(
                errorMessage = "Please fill all fields"
            )

            return
        }


        // Email validation
        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(currentState.email.trim())
                .matches()
        ) {

            _uiState.value = currentState.copy(
                errorMessage = "Please enter a valid email address"
            )

            return
        }

        // Phone validation
        if (!currentState.phone.matches(
                Regex("^[0-9]{10}$"))) {

            _uiState.value = currentState.copy(
                errorMessage = "Phone number must be exactly 10 digits"
            )

            return
        }



        // -----------------------------------------------------
        // Password validation
        // -----------------------------------------------------

        if (currentState.password != currentState.confirmPassword) {

            _uiState.value = currentState.copy(
                errorMessage = "Passwords do not match"
            )

            Log.d(
                "HIRELY_SIGNUP",
                "PASSWORDS DO NOT MATCH"
            )

            return
        }


        // -----------------------------------------------------
        // User object create
        // -----------------------------------------------------

        val user = User(
            name = currentState.name,
            email = currentState.email,
            phone = currentState.phone,
            password = currentState.password
        )


        // -----------------------------------------------------
        // Save user in DataStore
        // -----------------------------------------------------

        viewModelScope.launch {

            try {

                // Registered user ko DataStore mein save karenge
                dataStoreManager.saveUser(user)

                Log.d(
                    "HIRELY_SIGNUP",
                    "USER SAVED: ${user.email}"
                )

                // Save successful hone ke baad navigation
                onSuccess()

            } catch (e: Exception) {

                // Agar DataStore mein koi error aaye
                Log.e(
                    "HIRELY_SIGNUP",
                    "ERROR SAVING USER",
                    e
                )

                _uiState.value = _uiState.value.copy(
                    errorMessage = "Something went wrong"
                )
            }
        }
    }
}