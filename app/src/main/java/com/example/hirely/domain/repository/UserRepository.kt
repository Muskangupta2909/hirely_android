package com.example.hirely.domain.repository

import com.example.hirely.domain.model.User

interface UserRepository {

    suspend fun insertUser(user: User)

    suspend fun getUserByEmail(email: String): User?

    suspend fun login(
        email: String,
        password: String
    ): User?
}