package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenTo: Token() {
    override var _type = TokenType.TO
    override var _originStr = "TO"
}