package com.example.hirely.domain.usecase

import com.example.hirely.domain.model.User
import com.example.hirely.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): User? {

        return userRepository.login(
            email = email,
            password = password
        )
    }
}