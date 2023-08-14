package com.example.spreadshop.logic.network

import com.example.spreadshop.logic.model.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryResponseService {
    @GET("searchcategory")
    fun getAllCategory(@Query("cmd") cmd: String): Call<CategoryResponse>
}

// http://localhost:8080/searchcategory?cmd=getallcategory