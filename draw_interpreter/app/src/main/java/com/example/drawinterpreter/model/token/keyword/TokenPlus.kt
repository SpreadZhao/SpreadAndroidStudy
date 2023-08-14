package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenPlus: Token() {
    override var _type = TokenType.PLUS
    override var _originStr = "PLUS"
}