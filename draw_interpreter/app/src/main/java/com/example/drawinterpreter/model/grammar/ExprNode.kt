package com.example.drawinterpreter.model.grammar

import com.example.drawinterpreter.model.token.Token

// 所有树结点的父类
open class ExprNode(protected val _token: Token = Token())