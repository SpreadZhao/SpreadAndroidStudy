package com.example.drawinterpreter.model.token.number

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenNum(
    invalue: Double
): Token() {
    override var _type: TokenType = TokenType.CONST_ID
    override var _originStr: String = "NUM"
    override var _value = invalue
    val value get() = _value
}