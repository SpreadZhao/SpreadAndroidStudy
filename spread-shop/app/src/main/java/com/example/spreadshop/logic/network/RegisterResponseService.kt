package com.example.spreadshop.logic.network

import com.example.spreadshop.logic.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RegisterResponseService {
    @GET("register")
    fun getRegisterResponse(@Query("username") username: String, @Query("password") password: String): Call<RegisterResponse>
}