package com.example.drawinterpreter

import com.example.drawinterpreter.model.token.function.*
import com.example.drawinterpreter.model.token.keyword.TokenDraw
import com.example.drawinterpreter.model.token.number.TokenNum
import com.example.drawinterpreter.model.token.number.TokenParam
import com.example.drawinterpreter.model.token.number.TokenPi
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testTokenPi() {
        val tokenPi = TokenPi()
        println("type: ${tokenPi.type}, value: ${tokenPi.value}, str: ${tokenPi.originStr}")
        // 会报错，因为常量的value不让设置
        //tokenPi.value = 2.2
        println("now value is ${tokenPi.value}")
    }
    @Test
    fun testParam() {
        /**
         * 一开始这里括号里写的是3.4，但是实际打印出来还是NaN。
         * 原因就是TokenParam中的构造函数的参数：
         *      _value: Double
         * _value会被优先识别成形参，而不是成员。因此我们需要在重写父类成员
         * 的地方将它附上值：
         *      override var _value: Double = _value
         * 等号左边的_value和右边的是不一样的，因此索性直接改名成invalue。
         *
         * 之所以这么写，和本次架构有很大关系。我们不希望所有的token有构造函数，
         * 而某些子类(比如param)又需要构造函数来传value，所以如果我们只想在子类
         * 里传这些值的话，就只能将这些值直接赋到父类的属性上。
         *
         * TokenNum类也是同理。
         */
        val tokenParam = TokenParam(3.4)
        // 报错，不能修改任何token的类型
        // tokenParam.type = TokenType.ERROR_TOKEN
        println("type: ${tokenParam.type}, value: ${tokenParam.value}, str: ${tokenParam.originStr}")
    }

    @Test
    fun testSin(){
        val sinFun = TokenSin()
        println("type: ${sinFun.type}, str: ${sinFun.originStr}")
        //sinFun.value 报错，不存在
        sinFun.function(1.2)
    }

    @Test
    fun testSplit(){
        val cmd = "origin is (800, 700); rot is 0;"
        val parts = cmd.split(";")
        println(parts)
    }

    @Test
    fun testRegex(){
        var cmd = "origin is (800, 700)"
        val regex = Regex("[a-zA-Z]+([a-zA-Z]|[0-9])")
        val matched = regex.containsMatchIn(input = cmd)
        println("matched?: $matched")
        val finding = regex.find(input = cmd)
        println("finding: ${finding?.value}")
        val allFinding = regex.findAll(input = cmd)
        val result = StringBuilder()
        for(f in allFinding){
            result.append(f.value + " ")
        }
        println("all finding: $result")

        val regexId = Regex("[a-zA-Z]+([a-zA-Z]|[0-9])?")
        val regexDigit = Regex("[0-9]+(\\.[0-9]*)?")
        val regexParam = Regex("$regexDigit | $regexId")
        val regexParamList = Regex("\\( *${regexParam} *, *${regexParam} *\\)")
        val ismatch = regexParam.containsMatchIn(input = "(100.2, 200)")
        println("param matched?: $ismatch")
        cmd = "haha (2, 4), + asdfl(100.2,555.3)(thanks, 5.5),,,,(4)(s)haha(  haha  , 5.555 )"
        val params = regexParamList.findAll(input = cmd)
        for(p in params){
            println("param: ${p.value}")
        }
    }

    /**
     * 2, 3, 4, haha, sin(haha), sin()
     */
    @Test
    fun testRegex2(){
        val regexId = Regex("[a-zA-Z]+([a-zA-Z]|[0-9])?")
        val regexDigit = Regex("[0-9]+(\\.[0-9]*)?")
        val regexAtom = Regex("$regexId|$regexDigit|((cos|sin|exp|ln|sqrt|tan)())|")
        val regexComponent = Regex("$regexAtom(\\^$regexAtom)?")
        val regexFactor = Regex("([\\+-])?$regexComponent")
        val regexTerm = Regex("$regexFactor(\\*|/$regexFactor)*")
        val regexExpr = Regex("$regexTerm(\\+|-$regexTerm)*")
        val regexParam = Regex("$regexDigit|$regexId")
        val regexParamList = Regex("\\( *(${regexParam}) *, *(${regexParam}) *\\)")
        val cmd = "haha (2, 4), + asdfl(100.2,555.3)(thanks, 5.5),,,,(4)(s)haha(  haha  , 5.555 )"
        val cmd2 = "(2+3, 5)"
        val params = regexParamList.findAll(input = cmd2)
        for(p in params){
            println("paramlist: ${p.value}")
            val finds = regexParam.findAll(input = p.value)
            val list = finds.toList()
            for(e in list){
                println("param: ${e.value}")
            }
        }
    }

    @Test
    fun testNum(){
        val tokenNum = TokenNum(1.1)
        val tokenCos = TokenCos()

    }

    @Test
    fun testHaveFun(){
        val tokenCos = TokenCos()
        val tokenExp = TokenExp()
        val tokenSqrt = TokenSqrt()
        val tokenPi = TokenPi()
        val tokenDraw = TokenDraw()
        val tokenNum = TokenNum(20.0)
        println("cos: ${tokenCos is HaveFunction}")
        println("pi: ${tokenPi.javaClass.interfaces.contains(HaveFunction::class.java)}")
        println("cos2: ${tokenCos.javaClass.interfaces.contains(HaveFunction::class.java)}")
        println("exp: ${tokenExp.javaClass.interfaces.contains(HaveFunction::class.java)}")
        println("sqrt: ${tokenSqrt.javaClass.interfaces.contains(HaveFunction::class.java)}")
        println("draw: ${tokenDraw.javaClass.interfaces.contains(HaveFunction::class.java)}")
        println("num: ${tokenNum.javaClass.interfaces.contains(HaveFunction::class.java)}")

        if(tokenCos.javaClass.interfaces.contains(HaveFunction::class.java)){
            val ftoken = tokenCos as HaveFunction
            ftoken.function(5.5)
        }
    }

    @Test
    fun testIsInt(){
        val num = 1.0
        val numInt = num.toInt()
        println("int: ${num.toInt()}")
        println(num % numInt.toDouble() == 0.0)
    }

}