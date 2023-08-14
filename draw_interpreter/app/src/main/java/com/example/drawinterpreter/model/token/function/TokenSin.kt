package com.example.drawinterpreter.model.token.function

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenSin: Token(), HaveFunction { // 这里调用两个参构造也行，是因为value有默认值NAN
//    val type get() = _type
//    val originStr get() = _originStr
    // 函数不可能有value，所以连getter都没有

    override var _type: TokenType = TokenType.FUNC
    override var _originStr: String = "SIN"

    override fun function(param: Double): Double {
        println("now doing sin function...")
        println("param is $param")
        return param
    }
}