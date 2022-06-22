package com.example.androidtest.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.repository.Repository
import com.example.androidtest.repository.room.AppDatabase
import com.example.androidtest.repository.room.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class VMFragmentLogin(application: Application) : AndroidViewModel(application) {
    private val db: AppDatabase = AppDatabase.getDatabase(application)

    fun validate(userName: String, password: String): Int {
        return when {
            userName.isEmpty() -> {
                1
            }
            password.isEmpty() -> {
                2
            }
            else -> {
                0
            }
        }
    }

    fun setUserLoggedIn(userName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = User(Random().nextInt(), userName, 1)
                Repository.loginUser(db, user)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    fun isUserLoggedIn(): Int {
        var isUserLoggedIn = 0
        viewModelScope.async(Dispatchers.IO) {
            val result = async { Repository.isUserLoggedIn(db) }
            isUserLoggedIn = result.await()
        }
        return isUserLoggedIn
    }

}