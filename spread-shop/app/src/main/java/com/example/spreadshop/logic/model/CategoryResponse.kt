package com.example.spreadshop.logic.model

/**
 * {
 *      success : true
 *      categories :
 *      [
 *          {"category" : "手机"},
 *          {},
 *          {}
 *      ]
 * }
 */

data class CategoryResponse(val success: Boolean, val categories: List<Category>)
data class Category(val category: String)
