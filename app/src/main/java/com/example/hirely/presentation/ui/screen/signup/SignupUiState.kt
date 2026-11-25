package com.example.hirely.presentation.ui.screen.signup

// Signup screen ki saari UI state ko represent karta hai
data class SignupUiState(

    // User ka entered name
    val name: String = "",

    // User ka entered email
    val email: String = "",

    // User ka entered phone number
    val phone: String = "",

    // User ka entered password
    val password: String = "",

    // User ka confirm password
    val confirmPassword: String = "",

    // Signup request chal rahi hai ya nahi
    val isLoading: Boolean = false,

    // Agar koi error aaye toh message yahan store hoga
    val errorMessage: String? = null
)