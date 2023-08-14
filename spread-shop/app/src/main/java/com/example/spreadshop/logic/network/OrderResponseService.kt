package com.example.spreadshop.logic.network

import com.example.spreadshop.logic.model.OrderResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OrderResponseService {
    @GET("order")
    fun requestOrder(@Query("username") username: String, @Query("goods_id") goods_id: Int, @Query("number") number: Int): Call<OrderResponse>
}