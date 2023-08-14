package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenFrom: Token() {
    override var _type = TokenType.FROM
    override var _originStr = "FROM"
}