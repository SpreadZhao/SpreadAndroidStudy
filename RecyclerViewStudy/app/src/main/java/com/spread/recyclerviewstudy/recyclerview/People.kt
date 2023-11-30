package com.spread.recyclerviewstudy.recyclerview

data class People(
    val name: String,
    val age: Int,
    val gender: Gender
)

sealed class Gender

data object Male : Gender()

data object Female : Gender()