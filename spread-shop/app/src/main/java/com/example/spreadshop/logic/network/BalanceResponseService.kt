package com.example.spreadshop.logic.network

import com.example.spreadshop.logic.model.BalanceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BalanceResponseService {
    @GET("balance")
    fun getBalance(@Query("username") username: String): Call<BalanceResponse>
}