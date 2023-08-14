package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenPower: Token() {
    override var _type = TokenType.POWER
    override var _originStr = "POWER"
}