package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenComma: Token() {
    override var _type = TokenType.COMMA
    override var _originStr = "COMMA"
}