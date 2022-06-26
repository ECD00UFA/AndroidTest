package com.example.androidtest.repository.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.androidtest.repository.room.entities.User

@Dao
interface UserDao {

    suspend fun setLoggedInUser(loggedInUser: User) {
        deleteUser(loggedInUser)
        insertUser(loggedInUser)
    }

    @Insert
    abstract suspend fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("Select is_logged_in from User where is_logged_in = 1")
    fun isUserLoggedIn(): Int

    @Query("select * from user where is_logged_in = 1 ")
    fun getLoggedInUser(): User

    @Query("delete from user")
    fun nukeTable()
}