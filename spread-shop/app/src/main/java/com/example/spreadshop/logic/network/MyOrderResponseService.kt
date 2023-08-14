package com.example.spreadshop.logic.network

import com.example.spreadshop.logic.model.MyOrderResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyOrderResponseService {
    @GET("showbag")
    fun getMyOrder(@Query("username") username: String): Call<MyOrderResponse>
}