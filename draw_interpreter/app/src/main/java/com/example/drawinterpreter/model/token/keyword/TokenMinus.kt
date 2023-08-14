package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenMinus: Token() {
    override var _type = TokenType.MINUS
    override var _originStr = "MINUS"
}