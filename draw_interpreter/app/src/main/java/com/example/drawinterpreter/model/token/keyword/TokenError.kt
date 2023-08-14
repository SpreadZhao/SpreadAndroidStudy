package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenError: Token() {
    override var _type = TokenType.ERROR_TOKEN
    override var _originStr = "ERROR"
}