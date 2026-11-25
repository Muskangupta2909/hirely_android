package com.example.hirely.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
//loacl room db
@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HirelyDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}