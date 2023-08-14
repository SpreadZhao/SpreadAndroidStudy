package com.example.drawinterpreter

import android.util.Log
import com.example.drawinterpreter.model.token.*
import com.example.drawinterpreter.model.token.function.TokenCos
import com.example.drawinterpreter.model.token.function.TokenExp
import com.example.drawinterpreter.model.token.function.TokenLn
import com.example.drawinterpreter.model.token.function.TokenSin
import com.example.drawinterpreter.model.token.keyword.*
import com.example.drawinterpreter.model.token.number.*


object Scanner {

    /*
        从前端返回的输入序列保存在这里，由于使用了split(";")，
        所以每一个元素中都是不含分号的结尾
     */
    private val statements = ArrayList<String>()
    /*
        识别结束后的所有Token序列存在这里，这是一个List的List,
        第一层中每个元素是一个句子的Token序列；
        第二层中是每个句子的各个Token
     */
    private val _allTokens = ArrayList<ArrayList<Token>>()
    val allTokens get() = _allTokens
    // private val tokensInStm = ArrayList<Token>()
    private var result = ""

//    val regexId = Regex("[a-zA-Z]+([a-zA-Z]|[0-9])?")
//    val regexDigit = Regex("[0-9]+(\\.[0-9]*)?")
//    val regexParam = Regex("$regexDigit|$regexId")
//    val regexParamList = Regex("\\( *(${regexParam}) *, *(${regexParam}) *\\)")

    // 识别一个id，以字母开头，后面跟上若干字母或者数字
    private val regexId = Regex("[a-zA-Z]+([a-zA-Z]|[0-9])?")
    // 识别一个数字，整数或者小数
    private val regexDigit = Regex("[0-9]+(\\.[0-9]*)?")
    // 识别单个符号，都是程序允许出现的单个符号
    private val regexSymbol = Regex("[,/\\(\\)\\*\\+\\-\\^]")


    /**
     * 下面是我废弃掉的正则表达式，这些不应该在词法分析阶段完成
     */
    private val regexParam = Regex("$regexDigit|$regexId")
    private val regexParamList = Regex("\\( *(${regexParam}) *, *(${regexParam}) *\\)")
    //private val regexParamList = Regex("\\( *${regexDigit} *, *${regexDigit} *\\)")



    fun getCmd(list: List<String>){
        statements.addAll(list)
    }

    fun doScan(){
        // 对前端的字符序列进行遍历，stm是一个句子的String字符串
        for(stm in statements){
            // 创建这个stm的Token序列，最后添加到allTokens中
            val tokensInStm = ArrayList<Token>()
            if(stm != ""){
                /*
                    对stm中的每个字符进行遍历，对于不同的情况，
                    按照不同的正则匹配不同的结果
                 */
                var i = 0
                while (i < stm.length){
                    if(stm[i].isLetter()){ // 识别所有字母，也就是关键字和参数
                        i = matchId(i, stm, tokensInStm)
                        continue
                    }else if(stm[i].isDigit()){ // 所有数字常量
                            i = matchDigit(i, stm, tokensInStm)
                        continue
                    }else if(stm[i] == ' '){    // 空格直接去掉
                        i++
                        continue
                    }else if((stm[i] == '/' && stm[i + 1] == '/') || (stm[i] == '-' && stm[i + 1] == '-')){
                        // 处理注释，也就是直接跳到下一个statement，有隐患
                        break
                    }else { // 匹配单个的符号
                        i = matchSymbol(i, stm, tokensInStm)
                        continue
                    }
                }

                // 手工加分号
                tokensInStm.add(TokenSemico())
                // 将结果添加到allTokens中
                _allTokens.add(tokensInStm)

                for(token in tokensInStm){
                    when (token) {
                        is TokenNum -> {
                            Log.d("itpt-scanner-doscan", "it is num! val: ${token.value}")
                        }
                        is TokenParamList -> {
                            Log.d("itpt-scanner-doscan", "param1: ${token.param1.value}, param2: ${token.param2.value}")
                        }
                        else -> {
                            Log.d("itpt-scanner-doscan", "Type: ${token.type}, Str: ${token.originStr}")
                        }
                    }
                }
                Log.d("itpt-scanner-doscan", "size: ${tokensInStm.size}")
                Log.d("itpt-scanner-doscan", "======================")


            } // end if(stm != null)
        }// end for (stm in statements)
        // allTokens.clear()
        // statements.clear()
    }

    fun clearAll(){
        //tokensInStm.clear()
        _allTokens.clear()
        statements.clear()
        result = ""
    }

    fun getResultAsString(): String {
        val builder = StringBuilder()
        // for in loop with index
        for((linNum, stmtks) in _allTokens.withIndex()){
            builder.appendLine("<Line ${linNum + 1}>")
            for((tkNum, token) in stmtks.withIndex()){
                builder.append("[token ${tkNum + 1}] type: ${token.type}, string: ${token.originStr}")
                when(token) {
                    is TokenE -> builder.append(", value: ${token.value}")
                    is TokenPi -> builder.append(", value: ${token.value}")
                    is TokenNum -> builder.append(", value: ${token.value}")
                    is TokenParam -> builder.append(", is variable: ${token.isVariable}")
                    is TokenParamList -> {
                        builder.append(", param1: ${
                                                        if(token.param1.isVariable) 
                                                            token.param1.originStr 
                                                        else token.param1.value
                                                    }," +
                                        " param2: ${
                                                        if(token.param2.isVariable)
                                                            token.param2.originStr
                                                        else token.param2.value
                                                    }")
                    }
                }
                builder.appendLine()
            }
            builder.appendLine("=============")
        }
        result = builder.toString()
        return result
    }

    private fun matchId(index: Int, statement: String, tokensInStm: ArrayList<Token>): Int{
        val res = regexId.find(input = statement, startIndex = index)
        var resIndex = index
        if(res != null){
            when {
                res.value.equals("origin", true) -> {
                    tokensInStm.add(TokenOrigin())
                    resIndex += "origin".length
                }
                res.value.equals("rot", true) -> {
                    tokensInStm.add(TokenRot())
                    resIndex += "rot".length
                }
                res.value.equals("is", true) -> {
                    tokensInStm.add(TokenIs())
                    resIndex += "is".length
                }
                res.value.equals("scale", true) -> {
                    tokensInStm.add(TokenScale())
                    resIndex += "scale".length
                }
                res.value.equals("for", true) -> {
                    tokensInStm.add(TokenFor())
                    resIndex += "for".length
                }
                res.value.equals("from", true) -> {
                    tokensInStm.add(TokenFrom())
                    resIndex += "from".length
                }
                res.value.equals("to", true) -> {
                    tokensInStm.add(TokenTo())
                    resIndex += "to".length
                }
                res.value.equals("step", true) -> {
                    tokensInStm.add(TokenStep())
                    resIndex += "step".length
                }
                res.value.equals("draw", true) -> {
                    tokensInStm.add(TokenDraw())
                    resIndex += "draw".length
                }
                res.value.equals("e", true) -> {
                    tokensInStm.add(TokenE())
                    resIndex += "e".length
                }
                res.value.equals("pi", true) -> {
                    tokensInStm.add(TokenPi())
                    resIndex += "pi".length
                }
                res.value.equals("cos", true) -> {
                    tokensInStm.add(TokenCos())
                    resIndex += "cos".length
                }
                res.value.equals("sin", true) -> {
                    tokensInStm.add(TokenSin())
                    resIndex += "sin".length
                }
                res.value.equals("exp", true) -> {
                    tokensInStm.add(TokenExp())
                    resIndex += "exp".length
                }
                res.value.equals("ln", true) -> {
                    tokensInStm.add(TokenLn())
                    resIndex += "ln".length
                }

                else -> { // 如果不是任何定义好的关键字，那么就看作变量参数处理
                    val p = TokenParam()
                    p.isVariable = true
                    p.setStr(res.value)
                    tokensInStm.add(p)
                    resIndex += res.value.length
                }
            }
        }else{
            tokensInStm.add(TokenError())
            resIndex++
        }
        return resIndex
    }

    private fun matchSymbol(index: Int, statement: String, tokensInStm: ArrayList<Token>): Int{
        val res = regexSymbol.find(input = statement, startIndex = index)
        if(res != null){
            when(res.value){
                "," -> tokensInStm.add(TokenComma())
                "(" -> tokensInStm.add(TokenLeftBracket())
                ")" -> tokensInStm.add(TokenRightBracket())
                "+" -> tokensInStm.add(TokenPlus())
                "-" -> tokensInStm.add(TokenMinus())
                "*" -> tokensInStm.add(TokenMul())
                "/" -> tokensInStm.add(TokenDiv())
                "^" -> tokensInStm.add(TokenPower())
            }
        }else{  // 这里也没找到的话，那就代表出现了本程序不可能出现的东西，报错\
            //statement[index]就是这个不该出现的字符
            tokensInStm.add(TokenError())
        }
        return index + 1
    }


    /**
     * 废弃的函数，不应该在词法分析阶段处理参数
     */
    private fun matchParam(index: Int, statement: String, tokensInStm: ArrayList<Token>): Int{
        var resIndex = index
        val res = regexParamList.find(input = statement, startIndex = index)
        // -> (t, 100)
        if(res != null){
//            val p1 = res.value.substring(res.value.indexOf("(") + 1, 5)
            val params = regexParam.findAll(input = res.value)
            val list = params.toList()
            if(list.size == 2){
                val param1 = list[0].value
                Log.d("itpt-scanner-matchparam", "param1: $param1")
                val param2 = list[1].value
                Log.d("itpt-scanner-matchparam", "param2: $param2")
                if(regexDigit.matches(param1) && regexDigit.matches(param2)){
                    tokensInStm.add(TokenParamList(param1.toDouble(), param2.toDouble()))
                }else if(!regexDigit.matches(param1) && regexDigit.matches(param2)){
                    // 如果p1是变量，p2是常数

                    val p1 = TokenParam()
                    p1.setStr(param1)
                    p1.isVariable = true

                    val p2 = TokenParam(param2.toDouble())

                    val tpl = TokenParamList()
                    tpl.param1 = p1
                    tpl.param2 = p2

                    tokensInStm.add(tpl)
                }else if(regexDigit.matches(param1) && !regexDigit.matches(param2)){
                    // 如果p1是常数，p2是变量
                    val p1 = TokenParam(param1.toDouble())

                    val p2 = TokenParam()
                    p2.setStr(param2)
                    p2.isVariable = true

                    val tpl = TokenParamList()
                    tpl.param1 = p1
                    tpl.param2 = p2

                    tokensInStm.add(tpl)
                }else{
                    // p1, p2都是变量
                    val p1 = TokenParam()
                    p1.setStr(param1)
                    p1.isVariable = true

                    val p2 = TokenParam()
                    p2.setStr(param2)
                    p2.isVariable = true

                    val tpl = TokenParamList()
                    tpl.param1 = p1
                    tpl.param2 = p2

                    tokensInStm.add(tpl)
                }
                resIndex += res.value.length
            }// end if(list.size == 2)
        }// end if(res != null)
        return resIndex
    }

    private fun matchDigit(index: Int, statement: String, tokensInStm: ArrayList<Token>): Int {
        var resIndex = index
        val res = regexDigit.find(input = statement, startIndex = index)
        if(res != null){
//            if(res.value.isDigitsOnly()){
            tokensInStm.add(TokenNum(res.value.toDouble()))
            resIndex += res.value.length
        }
        return resIndex
    }

//    private fun isDouble(str: String): Boolean{
//        val pattern = Pattern.compile("^[]")
//    }

}