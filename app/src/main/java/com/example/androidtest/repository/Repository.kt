package com.example.androidtest.repository

import androidx.lifecycle.MutableLiveData
import com.example.androidtest.repository.retrofit.RestClient
import com.example.androidtest.repository.retrofit.model.Item
import com.example.androidtest.repository.room.AppDatabase
import com.example.androidtest.repository.room.entities.User
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

object Repository {
    var dataIndex = -1
    suspend fun loginUser(db: AppDatabase, user: User) {
        db.userDao().setLoggedInUser(user)
    }

    fun isUserLoggedIn(db: AppDatabase): Int {
        return db.userDao().isUserLoggedIn()
    }

    fun getLoggedInUser(db: AppDatabase): User {
        return db.userDao().getLoggedInUser()
    }

    fun logoutUser(db: AppDatabase) {
        db.userDao().nukeTable()
    }

    fun getData(data: MutableLiveData<ArrayList<Item>>) {
        dataIndex = -1
        RestClient.getInstance()?.getItems()
            ?.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        val map: Map<*, *> =
                            Gson().fromJson(response.body()?.toString(), MutableMap::class.java)
                        response.body()?.let { parseData(map as Map<String, *>, data) }
                    } else {
                        data.postValue(null)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    data.postValue(null)
                }
            })
    }

    private fun parseData(response: Map<String, *>, data: MutableLiveData<ArrayList<Item>>) {
        for (key in response.keys) {
            val item = response[key]
            if (item is ArrayList<*>) {
                for (index in item) {
                    parseData(index as Map<String, *>, data)
                }
            } else {
                if (data.value == null) {
                    data.value = arrayListOf()
                }
                when (key) {
                    "name" -> {
                        ++dataIndex
                        val dataItem = Item(item as String, "", "")
                        data.value?.add(dataItem)
                    }
                    "dose" -> {
                        if ((item as String).isEmpty()) {
                            data.value?.get(dataIndex)?.dose = "N/A"
                        } else {
                            data.value?.get(dataIndex)?.dose = item as String
                        }
                    }
                    "strength" -> {
                        data.value?.get(dataIndex)?.strength = item as String
                    }
                }
            }
        }
    }
}