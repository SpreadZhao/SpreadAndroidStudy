package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenLeftBracket: Token() {
    override var _type = TokenType.L_BRACKET
    override var _originStr = "L_BRACKET"
}