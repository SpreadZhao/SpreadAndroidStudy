package com.example.drawinterpreter.model.token.function

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenLn: Token(), HaveFunction {

//    val type get() = _type
//    val originStr get() = _originStr
    // 函数不可能有value，所以连getter都没有

    override var _type: TokenType = TokenType.FUNC
    override var _originStr: String = "LN"

    override fun function(param: Double): Double {
        println("now doing ln function...")
        println("param is $param")
        return param
    }
}