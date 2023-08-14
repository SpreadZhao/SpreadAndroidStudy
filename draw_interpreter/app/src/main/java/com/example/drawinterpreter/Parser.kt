package com.example.drawinterpreter

import android.util.Log
import com.example.drawinterpreter.model.draw.InterpretResult
import com.example.drawinterpreter.model.grammar.*
import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType
import com.example.drawinterpreter.model.token.number.TokenNum

object Parser{

    // 存Parser从Scanner那里拿来的分析结果
    private val allTokens = ArrayList<ArrayList<Token>>()
    private val result = ArrayList<ExprNode>()
    private val resBuilder = StringBuilder()

    // 解释结果，存所有画图相关的信息或者报错信息
    private val _interpretResult = InterpretResult()
    val interpretResult get() = _interpretResult

    private var statementIndex = 0
    private var tokenIndex = 0


    private val currentToken get() = allTokens[statementIndex][tokenIndex]
    private val lastToken get() = allTokens[statementIndex][tokenIndex - 1]

    fun getAllTokensFromScanner(list: ArrayList<ArrayList<Token>>){
        allTokens.addAll(list)
        for((index, stm) in allTokens.withIndex()){
            Log.d("itpt-parser-getall", "[line $index]")
            for(tk in stm){
                Log.d("itpt-parser-getall", "type: ${tk.type}, str: ${tk.originStr}")
            }
        }
    }



    fun testResult(): String{

        /**
         * 测试代码，伴随result成员使用，如果没有add操作，
         * 下面的代码不会被执行。
         */
        for(tree in result){
            when(tree){
                is BinExprNode -> resBuilder.append("result: ${tree.calculate()}")
                is UniExprNode -> resBuilder.append("result: ${tree.calculate()}")
                is NumExprNode -> resBuilder.append("single number: ${tree.value}")
                is ParamExprNode -> resBuilder.append("param: ${tree.token.originStr}")
            }
            resBuilder.appendLine()
        }

        /**
         * 展示interpreteResult的成员
         */
        resBuilder.appendLine("below is interpreter result: ")
        resBuilder.appendLine("origin x: ${_interpretResult.originX}")
        resBuilder.appendLine("origin y: ${_interpretResult.originY}")
        resBuilder.appendLine("rot: ${_interpretResult.rot}")
        resBuilder.appendLine("scale x: ${_interpretResult.scaleX}")
        resBuilder.appendLine("scale y: ${_interpretResult.scaleY}")
        resBuilder.appendLine("param: ${_interpretResult.param}")
        resBuilder.appendLine("from: ${_interpretResult.from}")
        resBuilder.appendLine("to: ${_interpretResult.to}")
        resBuilder.appendLine("step: ${_interpretResult.step}")
        resBuilder.appendLine("draw x: ${_interpretResult.drawX}")
        resBuilder.appendLine("draw x is param: ${_interpretResult.isDrawXParam}")
        resBuilder.appendLine("draw y: ${_interpretResult.drawY}")
        resBuilder.appendLine("draw y is param: ${_interpretResult.isDrawYParam}")
        return resBuilder.toString()
    }

    fun doParse() = program()

    private fun program(){
        /*
            执行分析整个输入序列，每次匹配一个statement和一个分号
         */
        while(statementIndex < allTokens.size){
            statement()
            matchToken(TokenType.SEMICO)
            statementIndex++
            tokenIndex = 0
        }
        if(_interpretResult.errorMessageList.isEmpty()){
            // 如果错误信息列表是空的，那就是没错，解释成功
            _interpretResult.isInterpretSuccessful = true
            Log.d("itpt-parser-result", "interpret result: ${_interpretResult.isInterpretSuccessful}")
        }else{
            // 否则就收集报错信息，准备展示给前端
            _interpretResult.collectErrorMsg()
        }
    }

    private fun statement(){
        when(currentToken.type){
            TokenType.ORIGIN -> originStatement()
            TokenType.SCALE -> scaleStatement()
            TokenType.ROT -> rotStatement()
            TokenType.FOR -> forStatement()
            TokenType.SEMICO -> {} // 整行注释，或者真就是个分号
            else -> syntaxError("unrecognized sentence type")
        }
    }

    private fun originStatement(){
        matchToken(TokenType.ORIGIN)
        matchToken(TokenType.IS)
        matchToken(TokenType.L_BRACKET)
//        result.add(expression())
        when(val res = expression()){
            is BinExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.originX = res.calculate()
                }else{
                    syntaxError("origin statement should not contain parameters: origin x")
                }
            }
            is UniExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.originX = res.calculate()
                }else{
                    syntaxError("origin statement should not contain parameters: origin x")
                }
            }
            is NumExprNode -> _interpretResult.originX = res.value
            is ParamExprNode -> syntaxError("origin statement should not contain parameters: origin x")
        }
        matchToken(TokenType.COMMA)
//        result.add(expression())
        when(val res = expression()){
            is BinExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.originY = res.calculate()
                }else{
                    syntaxError("origin statement should not contain parameters: origin y")
                }
            }
            is UniExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.originY = res.calculate()
                }else{
                    syntaxError("origin statement should not contain parameters: origin y")
                }
            }
            is NumExprNode -> _interpretResult.originY = res.value
            is ParamExprNode -> syntaxError("origin statement should not contain parameters: origin y")
        }
        matchToken(TokenType.R_BRACKET)
    }

    private fun scaleStatement(){
        matchToken(TokenType.SCALE)
        matchToken(TokenType.IS)
        matchToken(TokenType.L_BRACKET)
//        result.add(expression())
        when(val res = expression()){
            is BinExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.scaleX = res.calculate()
                }else{
                    syntaxError("scale statement should not contain parameters: scale x")
                }
            }
            is UniExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.scaleX = res.calculate()
                }else{
                    syntaxError("scale statement should not contain parameters: scale x")
                }
            }
            is NumExprNode -> _interpretResult.scaleX = res.value
            is ParamExprNode -> syntaxError("scale statement should not contain parameters: scale x")
        }
        matchToken(TokenType.COMMA)
//        result.add(expression())
        when(val res = expression()){
            is BinExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.scaleY = res.calculate()
                }else{
                    syntaxError("scale statement should not contain parameters: scale y")
                }
            }
            is UniExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.scaleY = res.calculate()
                }else{
                    syntaxError("scale statement should not contain parameters: scale y")
                }
            }
            is NumExprNode -> _interpretResult.scaleY = res.value
            is ParamExprNode -> syntaxError("scale statement should not contain parameters: scale y")
        }
        matchToken(TokenType.R_BRACKET)
    }

    private fun rotStatement(){
        matchToken(TokenType.ROT)
        matchToken(TokenType.IS)
//        result.add(expression())
        when(val res = expression()){
            is BinExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.rot = res.calculate()
                }else{
                    syntaxError("rot statement should not contain parameters")
                }
            }
            is UniExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.rot = res.calculate()
                }else{
                    syntaxError("rot statement should not contain parameters")
                }
            }
            is NumExprNode -> _interpretResult.rot = res.value
            is ParamExprNode -> syntaxError("rot statement should not contain parameters")
        }
    }

    private fun forStatement(){
        matchToken(TokenType.FOR)
        matchToken(TokenType.PARAM)
        val paramName = lastToken.originStr
        matchToken(TokenType.FROM)
//        result.add(expression())
        when(val res = expression()){
            is BinExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.from = res.calculate()
                }else{
                    syntaxError("from should not contain parameters")
                }
            }
            is UniExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.from = res.calculate()
                }else{
                    syntaxError("from should not contain parameters")
                }
            }
            is NumExprNode -> _interpretResult.from = res.value
            is ParamExprNode -> syntaxError("from should not contain parameters")
        }
        matchToken(TokenType.TO)
//        result.add(expression())
        when(val res = expression()){
            is BinExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.to = res.calculate()
                }else{
                    syntaxError("to should not contain parameters")
                }
            }
            is UniExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.to = res.calculate()
                }else{
                    syntaxError("to should not contain parameters")
                }
            }
            is NumExprNode -> _interpretResult.to = res.value
            is ParamExprNode -> syntaxError("to should not contain parameters")
        }
        matchToken(TokenType.STEP)
//        result.add(expression())
        when(val res = expression()){
            is BinExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.step = res.calculate()
                }else{
                    syntaxError("step should not contain parameters")
                }
            }
            is UniExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.step = res.calculate()
                }else{
                    syntaxError("step should not contain parameters")
                }
            }
            is NumExprNode -> _interpretResult.step = res.value
            is ParamExprNode -> syntaxError("step should not contain parameters")
        }
        matchToken(TokenType.DRAW)
        matchToken(TokenType.L_BRACKET)
//        result.add(expression())
        when(val res = expression()){
            is BinExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.drawX = res.calculate()
                } else{
                    if(paramName == res.paramName){
                        _interpretResult.isDrawXParamExp = true
                        _interpretResult.paramNodeX = res
                    }else{
                        syntaxError("wrong param: ${res.paramName} and $paramName")
                    }
                }
            }
            is UniExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.drawX = res.calculate()
                } else{
                    if(paramName == res.paramName){
                        _interpretResult.isDrawXParamExp = true
                        _interpretResult.paramNodeX = res
                    }else{
                        syntaxError("wrong param: ${res.paramName} and $paramName")
                    }
                }
            }
            is NumExprNode -> _interpretResult.drawX = res.value
            is ParamExprNode -> {
//                _interpretResult.param = if(res.token.originStr == paramName) paramName else ""
                if(res.token.originStr == paramName){
                    _interpretResult.param = paramName
                    _interpretResult.isDrawXParam = true
                }else{
                    syntaxError("wrong param: ${res.token.originStr} and $paramName")
                }
            }
        }
        matchToken(TokenType.COMMA)
//        result.add(expression())
        when(val res = expression()){
            is BinExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.drawY = res.calculate()
                } else{
                    if(paramName == res.paramName){
                        _interpretResult.isDrawYParamExp = true
                        _interpretResult.paramNodeY = res
                    }else{
                        syntaxError("wrong param: ${res.paramName} and $paramName")
                    }
                }
            }
            is UniExprNode -> {
                res.testHaveParam()
                if(!res.haveParam){
                    _interpretResult.drawY = res.calculate()
                } else{
                    if(paramName == res.paramName){
                        _interpretResult.isDrawYParamExp = true
                        _interpretResult.paramNodeY = res
                    }else{
                        syntaxError("wrong param: ${res.paramName} and $paramName")
                    }
                }
            }
            is NumExprNode -> _interpretResult.drawY = res.value
            is ParamExprNode -> {
//                _interpretResult.param = if(res.token.originStr == paramName) paramName else ""
                if(paramName == res.token.originStr){
                    _interpretResult.param = paramName
                    _interpretResult.isDrawYParam = true
                }else{
                    syntaxError("wrong param: ${res.token.originStr} and $paramName")
                }
            }
        }
        matchToken(TokenType.R_BRACKET)
    }

    private fun matchToken(type: TokenType){
        if(currentToken.type == TokenType.ERROR_TOKEN){
            // 如果是Error Token，那就直接跳过，匹配下一个看是不是type
            tokenIndex++
            syntaxError("bad token")
        }
        if(currentToken.type != type) syntaxError("token with mismatched type: ${currentToken.originStr}(${currentToken.type}), should be $type")
        Log.d("itpt-parser-match", "matched token: ${currentToken.type}")
        tokenIndex++
    }

    private fun expression(): ExprNode{
        var left = term()
        while(currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS){
            val ct = currentToken
            matchToken(ct.type)
            var right = term()
            left = makeExprNode(ct, left, right)
        }
        return left
    }

    private fun term(): ExprNode{
        var left = factor()
        while(currentToken.type == TokenType.MUL || currentToken.type == TokenType.DIV){
            val ct = currentToken
            matchToken(ct.type)
            var right = factor()
            left = makeExprNode(ct, left, right)
        }
        return left
    }

    private fun factor(): ExprNode{
        var left: ExprNode
        var right: ExprNode
        when (currentToken.type) {
            TokenType.PLUS -> {
                matchToken(TokenType.PLUS)
                right = factor()
            }
            TokenType.MINUS -> {
                val ct = currentToken
                matchToken(TokenType.MINUS)
                right = factor()
                left = NumExprNode(TokenNum(0.0))
                right = makeExprNode(ct, left, right)
            }
            else -> {
                right = component()
            }
        }
        return right
    }

//    private fun factor(): ExprNode{
//        var left: ExprNode
//        var right: ExprNode
//        when (currentToken.type) {
//            TokenType.PLUS -> {
//                matchToken(TokenType.PLUS)
//                right = factor()
//            }
//            TokenType.MINUS -> {
//                val ct = currentToken
//                matchToken(TokenType.MINUS)
//                right = factor()
//                left = NumExprNode(TokenNum(0.0))
//                right = makeExprNode(ct, left, right)
//            }
//            else -> {
//                right = component()
//            }
//        }
//        return right
//    }

    private fun component(): ExprNode{
        var left = atom()
        if(currentToken.type == TokenType.POWER){
            val ct = currentToken
            matchToken(TokenType.POWER)
            var right = component()
            left = makeExprNode(ct, left, right)
        }
        return left
    }

    private fun atom(): ExprNode{
        return when(currentToken.type){
            TokenType.CONST_ID -> {
                matchToken(TokenType.CONST_ID)
                makeExprNode(lastToken)
            }
            TokenType.PARAM -> {
                matchToken(TokenType.PARAM)
                makeExprNode(lastToken)
            }
            TokenType.FUNC -> {
                val ct = currentToken
                matchToken(TokenType.FUNC)
                matchToken(TokenType.L_BRACKET)
                val exp = expression()
                matchToken(TokenType.R_BRACKET)
                makeExprNode(ct, exp)
            }
            TokenType.L_BRACKET -> {
                matchToken(TokenType.L_BRACKET)
                val exp = expression()
                matchToken(TokenType.R_BRACKET)
                exp
            }
            else -> {
                syntaxError("uncertain type in expression")
                ExprNode()
            }
        }
    }


//    private fun makeExprNode(token: Token, left: ExprNode, right: ExprNode): ExprNode {
        //        when(token.type){
//            TokenType.PLUS -> {
//                binExprNode = BinExprNode(token, left, right)
//            }
//            TokenType.MINUS -> {
//                binExprNode = BinExprNode(token, left, right)
//            }
//            TokenType.MUL -> {
//                binExprNode = BinExprNode(token, left, right)
//            }
//            TokenType.DIV -> {
//                binExprNode = BinExprNode(token, left, right)
//            }
//            TokenType.POWER -> {
//                binExprNode = BinExprNode(token, left, right)
//            }
//            else -> {
//                binExprNode = BinExprNode()
//            }
//        }
//        return BinExprNode(token, left, right)
//    }


    private fun makeExprNode(token: Token, left: ExprNode, right: ExprNode) = BinExprNode(token, left, right)
    private fun makeExprNode(token: Token, child: ExprNode) = UniExprNode(token, child)
    private fun makeExprNode(token: Token) = when(token.type){
        TokenType.CONST_ID -> NumExprNode(token)
        TokenType.PARAM -> ParamExprNode(token)
        else -> {
            syntaxError("impossible error")
            ExprNode()
        }
    }




    private fun syntaxError(msg: String){
        Log.d("itpt-parsesr-error", "Syntax Error")
        _interpretResult.errorMessageList.add("$msg ($statementIndex)($tokenIndex)")
    }



    private fun preOrder(node: ExprNode){
        when (node) {
            is BinExprNode -> {
                preOrder(node.left)
                resBuilder.append(node.token.originStr).append(" ")
                preOrder(node.right)
            }
            is UniExprNode -> {
                preOrder(node.child)
                resBuilder.append(node.token.originStr).append(" ")
            }
            is NumExprNode -> {
                resBuilder.append(node.value).append(" ")
            }
            is ParamExprNode -> {
                resBuilder.append(node.token.originStr).append(" ")
            }
        }
    }

    fun clearAll(){
        allTokens.clear()
        resBuilder.clear()
        result.clear()
        statementIndex = 0
        tokenIndex = 0
    }


}