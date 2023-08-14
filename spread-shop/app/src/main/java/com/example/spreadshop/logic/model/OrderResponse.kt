package com.example.spreadshop.logic.model

data class OrderResponse(val success: Boolean, val order: Order)
data class Order(val username: String, val goods_id: Int, val num: Int, val message: String)
data class MyOrderResponse(val success: Boolean, val orderList: List<Bag>)
data class Bag(val order_id: Int, val goods_id: Int, val order_time: String, val account_name: String, val goods_number: Int, val goods_name: String)

