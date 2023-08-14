package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenIs: Token(){
    override var _type: TokenType = TokenType.IS
    override var _originStr: String = "IS"
}