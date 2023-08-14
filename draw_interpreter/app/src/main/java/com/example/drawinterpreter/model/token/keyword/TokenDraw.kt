package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenDraw: Token() {
    override var _type = TokenType.DRAW
    override var _originStr = "DRAW"
}