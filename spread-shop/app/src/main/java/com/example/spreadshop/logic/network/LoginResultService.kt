package com.example.spreadshop.logic.network

import com.example.spreadshop.logic.model.LoginResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginResultService {
    @GET("login")
    fun getLoginResult(@Query("username") username: String, @Query("password") password: String): Call<LoginResult>
}