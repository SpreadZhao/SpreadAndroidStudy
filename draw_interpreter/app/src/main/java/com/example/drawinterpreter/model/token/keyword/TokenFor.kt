package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenFor: Token() {
    override var _type = TokenType.FOR
    override var _originStr = "FOR"
}