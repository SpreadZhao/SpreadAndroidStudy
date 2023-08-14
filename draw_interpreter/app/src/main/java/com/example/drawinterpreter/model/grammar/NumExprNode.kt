package com.example.drawinterpreter.model.grammar

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.number.TokenE
import com.example.drawinterpreter.model.token.number.TokenNum
import com.example.drawinterpreter.model.token.number.TokenParam
import com.example.drawinterpreter.model.token.number.TokenPi

// 所有数值的树结点
class NumExprNode(
    _token: Token
): ExprNode(_token) {
    val value: Double get() {
        return when(_token){
            is TokenE -> _token.value
            is TokenNum -> _token.value
            is TokenPi -> _token.value
//            is TokenParam -> {
//                if(!token.isVariable) token.value
//                else Double.MIN_VALUE
//            }
            else -> Double.MIN_VALUE
        }
    }
}