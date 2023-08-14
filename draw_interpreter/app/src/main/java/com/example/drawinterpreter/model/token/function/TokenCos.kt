package com.example.drawinterpreter.model.token.function

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenCos: Token(), HaveFunction {

//    override _type: TokenType = TokenType.FUNC,
//    _originStr: String = "COS"
//    val type get() = _type
//    val originStr get() = _originStr
    // 函数不可能有value，所以连getter都没有
//    init {
//        //_type = TokenType.FUNC
//        _originStr = "COS"
//    }

    override var _type = TokenType.FUNC
    override var _originStr = "COS"


    override fun function(param: Double): Double {
        println("now doing cos function...")
        println("param is $param")
        return param
    }

}