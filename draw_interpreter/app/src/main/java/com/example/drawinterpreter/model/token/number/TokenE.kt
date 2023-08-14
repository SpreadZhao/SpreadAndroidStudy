package com.example.drawinterpreter.model.token.number

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType
import kotlin.math.E

class TokenE: Token() {

//    val type get() = _type
//    val originStr get() = _originStr

    override var _type: TokenType = TokenType.CONST_ID
    override var _originStr: String = "E"
    override var _value: Double = E

    val value get() = _value

//    override fun function(param: Double): Double {
//        println("you should not call this function: E")
//        return Double.NaN
//    }
}