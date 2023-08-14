package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenOrigin: Token() {

    override var _type: TokenType = TokenType.ORIGIN
    override var _originStr: String = "ORIGIN"
//    val type get() = _type
//    val originStr get() = _originStr
}