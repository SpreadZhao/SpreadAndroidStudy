package com.example.drawinterpreter.model.draw

import com.example.drawinterpreter.model.grammar.ExprNode

class InterpretResult {
    private var _originX = Double.MIN_VALUE
    private var _originY = Double.MIN_VALUE
    private var _rot = Double.MIN_VALUE
    private var _scaleX = Double.MIN_VALUE
    private var _scaleY = Double.MIN_VALUE
    private var _param = ""
    private var _from = Double.MIN_VALUE
    private var _to = Double.MIN_VALUE
    private var _step = Double.MIN_VALUE
    private var _drawX = Double.MIN_VALUE
    private var _drawY = Double.MIN_VALUE
    private lateinit var _paramNodeX: ExprNode
    private lateinit var _paramNodeY: ExprNode
    private val _errorMessageList = ArrayList<String>()
    private var _errorMessage = ""

    var isDrawXParam = false
    var isDrawYParam = false
    var isDrawXParamExp = false
    var isDrawYParamExp = false
    var isInterpretSuccessful = false

    var originX get() = _originX
        set(value) {
            _originX = value
        }

    var originY get() = _originY
        set(value) {
            _originY = value
        }

    var rot get() = _rot
        set(value) {
            _rot = value
        }

    var scaleX get() = _scaleX
        set(value) {
            _scaleX = value
        }

    var scaleY get() = _scaleY
        set(value) {
            _scaleY = value
        }


    var from get() = _from
        set(value) {
            _from = value
        }

    var to get() = _to
        set(value) {
            _to = value
        }

    var step get() = _step
        set(value) {
            _step = value
        }

    var drawX get() = _drawX
        set(value) {
            _drawX = value
        }

    var drawY get() = _drawY
        set(value) {
            _drawY = value
        }

    var param get() = _param
        set(value) {
            _param = value
        }

    var paramNodeX get() = _paramNodeX
        set(value) {
            _paramNodeX = value
        }

    var paramNodeY get() = _paramNodeY
        set(value) {
            _paramNodeY = value
        }

    val errorMessageList get() = _errorMessageList

    val errorMessage get() = _errorMessage

    fun collectErrorMsg(){
        val bd = StringBuilder()
        for((idx, msg) in _errorMessageList.withIndex()){
            bd.appendLine("[${idx + 1}] $msg")
        }
        _errorMessage = bd.toString()
    }


    fun resetAll(){
        _originX = Double.MIN_VALUE
        _originY = Double.MIN_VALUE
        _rot = Double.MIN_VALUE
        _scaleX = Double.MIN_VALUE
        _scaleY = Double.MIN_VALUE
        _param = ""
        _from = Double.MIN_VALUE
        _to = Double.MIN_VALUE
        _step = Double.MIN_VALUE
        _drawX = Double.MIN_VALUE
        _drawY = Double.MIN_VALUE
        _errorMessageList.clear()
        _errorMessage = ""

        isDrawXParam = false
        isDrawYParam = false
        isDrawXParamExp = false
        isDrawYParamExp = false
        isInterpretSuccessful = false
    }
}