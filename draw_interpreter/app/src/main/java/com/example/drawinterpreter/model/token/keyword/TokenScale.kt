package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenScale: Token() {
    override var _type: TokenType = TokenType.SCALE
    override var _originStr: String = "SCALE"
//    val type get() = _type
//    val originStr get() = _originStr
}