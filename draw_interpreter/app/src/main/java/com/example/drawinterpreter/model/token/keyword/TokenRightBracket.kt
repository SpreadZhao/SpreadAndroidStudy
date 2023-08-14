package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenRightBracket: Token() {
    override var _type = TokenType.R_BRACKET
    override var _originStr = "R_BRACKET"
}