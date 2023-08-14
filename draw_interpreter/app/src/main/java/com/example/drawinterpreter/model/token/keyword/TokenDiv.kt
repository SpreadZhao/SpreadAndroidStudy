package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenDiv: Token() {
    override var _type = TokenType.DIV
    override var _originStr = "DIV"
}