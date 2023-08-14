package com.example.drawinterpreter.model.token.keyword

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

class TokenComment: Token() {
    override var _type = TokenType.COMMENT
    override var _originStr = "//"

    fun setStr(str: String){
        _originStr = str
    }
}