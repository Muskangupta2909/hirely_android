package com.example.hirely.presentation.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hirely.data.AdminCredentials
import com.example.hirely.data.DataStoreManager
import com.example.hirely.domain.usecase.LoginUseCase
import com.example.hirely.presentation.ui.screen.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())

    val uiState: StateFlow<LoginUiState> =
        _uiState.asStateFlow()

    private val dataStoreManager =
        DataStoreManager(context)


    // ---------------------------------------------------------
    // Email / Phone
    // ---------------------------------------------------------

    fun onEmailChange(email: String) {

        _uiState.value = _uiState.value.copy(
            email = email,
            errorMessage = null
        )
    }


    // ---------------------------------------------------------
    // Password
    // ---------------------------------------------------------

    fun onPasswordChange(password: String) {

        _uiState.value = _uiState.value.copy(
            password = password,
            errorMessage = null
        )
    }


    // ---------------------------------------------------------
    // Login
    // ---------------------------------------------------------

    fun login(
        onLoginSuccess: (String) -> Unit
    ) {

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


        // Email validation
        if (!Patterns.EMAIL_ADDRESS
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


        viewModelScope.launch {

            // =================================================
            // ADMIN LOGIN CHECK
            // =================================================

            val isAdmin =
                currentState.email.trim().equals(
                    AdminCredentials.EMAIL,
                    ignoreCase = true
                ) &&
                        currentState.password ==
                        AdminCredentials.PASSWORD


            // =================================================
            // ADMIN LOGIN
            // =================================================

            if (isAdmin) {

                dataStoreManager.saveLoginData(
                    email = AdminCredentials.EMAIL
                )

                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = null,
                    role = "ADMIN"
                )

                onLoginSuccess("ADMIN")

                return@launch
            }


            // =================================================
            // NORMAL USER LOGIN
            // =================================================

            val user = loginUseCase(
                email = currentState.email.trim(),
                password = currentState.password
            )


            // -------------------------------------------------
            // User not found
            // -------------------------------------------------

            if (user == null) {

                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "Invalid email or password"
                )

                return@launch
            }


            // -------------------------------------------------
            // User valid
            // -------------------------------------------------

            dataStoreManager.saveLoginData(
                email = user.email
            )

            _uiState.value = currentState.copy(
                isLoading = false,
                errorMessage = null,
                role = "USER"
            )

            onLoginSuccess("USER")
        }
    }
}