package com.example.drawinterpreter.model.token.number

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType

/**
 * 已经废弃的类，在词法分析阶段不应出现它
 */
class TokenParamList(
    param1val: Double = Double.NaN,
    param2val: Double = Double.NaN,
): Token(){
    override var _type: TokenType = TokenType.PARAM
    override var _originStr: String = "PARAM_CONFIG"

    var param1: TokenParam = TokenParam(param1val)
    var param2: TokenParam = TokenParam(param2val)
}