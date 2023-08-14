package com.example.drawinterpreter.model.token.function

/**
 * 已经废弃的接口，并不需要给结点去存函数
 */
// 所有函数token的共同特点：have a function to call
interface HaveFunction {
    fun function(param: Double): Double{
        println("functions have one param. If you see this, it means you have not implement it yet.")
        return Double.MIN_VALUE
    }
    fun function(param1: Double, param2: Double): Double{
        println("functions have two params. If you see this, it means you have not implement it yet.")
        return Double.MIN_VALUE
    }
}