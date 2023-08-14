package com.example.drawinterpreter.model.grammar

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.number.TokenParam

class ParamExprNode(
    _token: Token
): ExprNode(_token){
    val token get() = _token
}