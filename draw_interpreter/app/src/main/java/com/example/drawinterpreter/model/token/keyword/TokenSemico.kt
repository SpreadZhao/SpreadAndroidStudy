package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenSemico: Token() {
    override var _type: TokenType = TokenType.SEMICO
    override var _originStr: String = "SEMICO"
}