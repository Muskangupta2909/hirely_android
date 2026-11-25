package com.example.hirely.di

import android.content.Context
import androidx.room.Room
import com.example.hirely.data.local.HirelyDatabase
import com.example.hirely.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideHirelyDatabase(
        @ApplicationContext context: Context
    ): HirelyDatabase {

        return Room.databaseBuilder(
            context,
            HirelyDatabase::class.java,
            "hirely_database"
        ).build()
    }

    @Provides
    fun provideUserDao(
        database: HirelyDatabase
    ): UserDao {
        return database.userDao()
    }
}