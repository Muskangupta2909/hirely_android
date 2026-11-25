package com.example.hirely.data.repository

import com.example.hirely.data.local.UserDao
import com.example.hirely.data.local.UserEntity
import com.example.hirely.domain.model.User
import com.example.hirely.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insertUser(user: User) {

        val userEntity = UserEntity(
            name = user.name,
            email = user.email,
            phone = user.phone,
            password = user.password
        )

        userDao.insertUser(userEntity)
    }

    override suspend fun getUserByEmail(email: String): User? {

        val userEntity = userDao.getUserByEmail(email)

        return userEntity?.let {
            User(
                name = it.name,
                email = it.email,
                phone = it.phone,
                password = it.password
            )
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): User? {

        val userEntity = userDao.getUserByCredentials(
            email = email,
            password = password
        )

        return userEntity?.let {
            User(
                name = it.name,
                email = it.email,
                phone = it.phone,
                password = it.password
            )
        }
    }
}