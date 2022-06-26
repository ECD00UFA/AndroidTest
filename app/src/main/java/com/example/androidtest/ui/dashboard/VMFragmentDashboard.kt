package com.example.androidtest.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidtest.repository.Repository
import com.example.androidtest.repository.retrofit.model.Item
import com.example.androidtest.repository.room.AppDatabase
import com.example.androidtest.repository.room.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VMFragmentDashboard(application: Application) : AndroidViewModel(application) {
    private val db: AppDatabase = AppDatabase.getDatabase(application)
    val data = MutableLiveData<ArrayList<Item>>()
    val user = MutableLiveData<User>()

    fun logoutUser(){
        viewModelScope.launch(Dispatchers.IO) {
            Repository.logoutUser(db)
        }
    }

    fun getUserDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            user.postValue(Repository.getLoggedInUser(db))
        }
    }

    fun getData() {
        Repository.getData(data)
    }
}