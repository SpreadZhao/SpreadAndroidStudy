package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenMul: Token() {
    override var _type = TokenType.MUL
    override var _originStr = "MUL"
}