package com.example.hirely.presentation.ui.screen.login

// Login screen ki saari UI state ko represent karta hai
data class LoginUiState(

    // User ka entered email
    val email: String = "",

    // User ka entered password
    val password: String = "",

    // Login request chal rahi hai ya nahi
    val isLoading: Boolean = false,

    // Agar koi error aaye toh message yahan store hoga
    val errorMessage: String? = null,

    // Logged-in user's role
    // Default USER rahega
    val role: String = "USER"
)