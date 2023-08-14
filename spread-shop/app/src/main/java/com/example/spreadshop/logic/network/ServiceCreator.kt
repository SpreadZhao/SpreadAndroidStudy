package com.example.spreadshop.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ServiceCreator {
    private const val BASE_URL_ZFH = "http://192.168.183.88:8080/"
    private const val BASE_URL_SPREAD = "http://192.168.190.39:8080/"
    private const val BASE_URL_GMY = "http://192.168.31.233:8080/"
    private const val BASE_URL_ZFH2 = "http://192.168.153.88:8080/"
    private const val CLASS = "http://192.168.21.39:8080/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(CLASS)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}