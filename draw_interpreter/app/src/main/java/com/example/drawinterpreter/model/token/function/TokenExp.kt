package com.example.drawinterpreter.model.token.function

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenExp: Token(), HaveFunction {

//    val type get() = _type
//    val originStr get() = _originStr
    // 函数不可能有value，所以连getter都没有
    override var _type = TokenType.FUNC
    override var _originStr: String = "EXP"

    override fun function(param1: Double, param2: Double): Double {
        println("now doing exp function...")
        println("params: $param1, $param2")
        return param1 + param2
    }
}