package com.example.drawinterpreter.model.grammar

import com.example.drawinterpreter.Parser
import com.example.drawinterpreter.model.token.Token
import com.example.drawinterpreter.model.token.TokenType
import kotlin.math.*

// + - 等二元运算的树结点
class BinExprNode(
    _token: Token,
    _left: ExprNode,
    _right: ExprNode
): ExprNode(_token) {
    val left = _left
    val right = _right
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

//    private fun preOrder(node: ExprNode){
//        when (node) {
//            is BinExprNode -> {
//                preOrder(node.left)
//                Parser.resBuilder.append(node.token.originStr).append(" ")
//                preOrder(node.right)
//            }
//            is UniExprNode -> {
//                preOrder(node.child)
//                Parser.resBuilder.append(node.token.originStr).append(" ")
//            }
//            is NumExprNode -> {
//                Parser.resBuilder.append(node.value).append(" ")
//            }
//            is ParamExprNode -> {
//                Parser.resBuilder.append(node.token.originStr).append(" ")
//            }
//        }
//    }
}