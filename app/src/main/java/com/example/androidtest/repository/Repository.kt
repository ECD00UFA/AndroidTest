package com.example.androidtest.repository

import com.example.androidtest.repository.room.AppDatabase
import com.example.androidtest.repository.room.entities.User

object Repository {
    suspend fun loginUser(db: AppDatabase, user: User) {
        db.userDao().setLoggedInUser(user)
    }

    fun deleteUser(db: AppDatabase, user: User) {
        db.userDao().deleteUser(user)
    }

    fun isUserLoggedIn(db: AppDatabase): Int {
        return db.userDao().isUserLoggedIn()
    }
}