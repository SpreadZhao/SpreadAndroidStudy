package com.example.spreadshop.logic.model

/**
 * {
 *      success : true
 *      goods : [
 *                  {},
 *                  {},
 *                  {}
 *              ]
 * }
 */

data class GoodsResponse(val success: Boolean, val goods: List<Goods>)
data class Goods(val goods_id: Int, val goods_name: String, val goods_category: String, val goods_storage: Int, val goods_price: Int)

