package com.example.hirely.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
//user data  operations
@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity)

    // Signup: check whether email already exists
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(
        email: String
    ): UserEntity?

    // Login: check email + password
    @Query("""
        SELECT * FROM users
        WHERE email = :email
        AND password = :password
        LIMIT 1
    """)
    suspend fun getUserByCredentials(
        email: String,
        password: String
    ): UserEntity?
}