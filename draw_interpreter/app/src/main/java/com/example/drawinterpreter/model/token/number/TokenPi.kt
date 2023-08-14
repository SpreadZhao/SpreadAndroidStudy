package com.example.drawinterpreter.model.token.number

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType
import kotlin.math.PI

class TokenPi: Token(){
    /**
     * 这里为什么是val而不是var？
     * 如果写var，会报如下错误：
     *      Property must be initialized
     * 因此只有不变的量才能只有get属性。任何会变的量都要有
     * setter才行，不然就不会被初始化。
     *
     * PI是常量，所以它的所有属性都只有getter，没有setter。
     */
//    val type: TokenType
//        get() = _type
//
//    val originStr: String
//        get() = _originStr

    override var _type: TokenType = TokenType.CONST_ID
    override var _originStr: String = "PI"
    override var _value: Double = PI

    val value: Double get() = _value
//        set(v){
//            println("[[[in token setter: value]]]")
//            _value = v
//        }

//    override fun function(param: Double): Double {
//        println("you should not call this function: PI")
//        return Double.NaN
//    }
}