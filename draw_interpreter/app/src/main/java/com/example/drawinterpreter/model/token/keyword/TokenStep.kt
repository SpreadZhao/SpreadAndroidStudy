package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenStep: Token() {
    override var _type = TokenType.STEP
    override var _originStr = "STEP"
}