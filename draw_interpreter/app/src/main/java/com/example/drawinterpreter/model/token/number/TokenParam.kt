package com.example.drawinterpreter.model.token.number

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenParam(
    invalue: Double = Double.NaN
): Token() {
//    val type get() = _type
//    val originStr get() = _originStr

    override var _type: TokenType = TokenType.PARAM
    override var _originStr: String = "T"
    override var _value: Double = invalue


    val value get() = _value

    var isVariable = false

    fun setStr(str: String){
        _originStr = str
    }


//    override fun function(param: Double): Double {
//        println("you should not call this function: Param")
//        return Double.NaN
//    }
}