package com.example.drawinterpreter.model.grammar

import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType
import com.example.drawinterpreter.model.token.function.HaveFunction
import kotlin.math.*

// 一元运算的树结点，通常都是函数，还有单值取负的运算
class UniExprNode(
    _token: Token,
    _child: ExprNode
): ExprNode(_token) {
    val child = _child
    val token get() = _token
    var haveParam = false
    var paramName = ""

    fun calculate(): Double{
        return postOrder(this)
    }

    fun testHaveParam(){
        testCore(this)
    }

    private fun testCore(node: ExprNode){
        when(node){
            is ParamExprNode -> {
                haveParam = true
                paramName = node.token.originStr
                return
            }
            is BinExprNode -> {
                testCore(node.left)
                testCore(node.right)
            }
            is UniExprNode -> {
                testCore(node.child)
            }
        }
    }

    private fun postOrder(node: ExprNode): Double{
        when(node){
            is BinExprNode -> {
                return when(node.token.type){
                    TokenType.PLUS -> postOrder(node.left) + postOrder(node.right)
                    TokenType.MINUS -> postOrder(node.left) - postOrder(node.right)
                    TokenType.MUL -> postOrder(node.left) * postOrder(node.right)
                    TokenType.DIV -> postOrder(node.left) / postOrder(node.right)
                    TokenType.POWER -> postOrder(node.left).pow(postOrder(node.right))
                    else -> Double.MIN_VALUE
                }
            }
            is UniExprNode -> {
                return when(node.token.originStr){
//                    "SIN" -> sin(node.child.value)
//                    "COS" -> cos(node.child.value)
//                    "TAN" -> tan(node.child.value)
//                    "LN" -> ln(node.child.value)
//                    "SQRT" -> sqrt(node.child.value)
                    "SIN" -> sin(postOrder(node.child))
                    "COS" -> cos(postOrder(node.child))
                    "TAN" -> tan(postOrder(node.child))
                    "LN" -> ln(postOrder(node.child))
                    "SQRT" -> sqrt(postOrder(node.child))
                    "EXP" -> exp(postOrder(node.child))
                    else -> Double.MIN_VALUE
                }
            }
            is NumExprNode -> return node.value
            else -> return Double.MIN_VALUE
        }
    }
//    fun callFunction(param: Double): Double{
//        if(_token.javaClass.interfaces.contains(HaveFunction::class.java)){
//            val ftoken = _token as HaveFunction
//            return ftoken.function(param)
//        }
//        return Double.MIN_VALUE
//    }
//    fun callFunction(param1: Double, param2: Double): Double{
//        if(_token.javaClass.interfaces.contains(HaveFunction::class.java)){
//            val ftoken = _token as HaveFunction
//            return ftoken.function(param1, param2)
//        }
//        return Double.MIN_VALUE
//    }
}