package com.example.hirely.domain.usecase

import com.example.hirely.domain.model.User
import com.example.hirely.domain.repository.UserRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val userRepository: UserRepository

) {

    suspend operator fun invoke(user: User) {

        val existingUser = userRepository.getUserByEmail(user.email)

        if (existingUser != null) {
            throw Exception("Email already registered")
        }

        userRepository.insertUser(user)
    }
}