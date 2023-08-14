package com.example.spreadshop.logic.network

import com.example.spreadshop.logic.model.Goods
import com.example.spreadshop.logic.model.GoodsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoodsService {
    @GET("searchgoods")
    fun getGoods(@Query("cmd") cmd: String): Call<GoodsResponse>
}
//
/*
* String age = people.getAge();
* val age = people.age
* */
// http:/searchgoods?cmd=showall
// http:/searchgoods?cmd=categoryshouji
// http:/searchgoods?cmd=getrecommand

