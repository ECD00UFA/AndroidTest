package com.example.androidtest.repository.retrofit

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/v3/fe787ef7-9f74-47fc-83c0-6af6ba91dbe9")
    fun getItems(): Call<String>
}
