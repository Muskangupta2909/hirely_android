package com.example.hirely.domain.model

// Registered user ki details represent karta hai
data class User(

    // User ka naam
    val name: String,

    // User ka email
    val email: String,

    // User ka phone number
    val phone: String,

    // User ka password
    val password: String,

    val role: String = "USER"
)