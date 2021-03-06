package com.example.androidtest.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidtest.repository.Repository
import com.example.androidtest.repository.room.AppDatabase
import com.example.androidtest.repository.room.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class VMFragmentLogin(application: Application) : AndroidViewModel(application) {
    private val db: AppDatabase = AppDatabase.getDatabase(application)
    val isUserLoggedIn: MutableLiveData<Int> = MutableLiveData()


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

    fun isUserLoggedIn() {
        viewModelScope.launch(Dispatchers.IO) {
            isUserLoggedIn.postValue(Repository.isUserLoggedIn(db))
        }
    }

}