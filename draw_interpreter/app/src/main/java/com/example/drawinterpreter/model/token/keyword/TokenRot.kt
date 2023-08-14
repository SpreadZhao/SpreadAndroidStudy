package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenRot: Token() {

    override var _type: TokenType = TokenType.ROT
    override var _originStr: String = "ROT"
//    val type get() = _type
//    val originStr get() = _originStr
}