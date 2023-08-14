package com.example.drawinterpreter.model.token

enum class TokenType{
    NULL,   // default value, every definite token should not take this.
    ID, COMMENT,
    ORIGIN, SCALE, ROT, IS,
    TO, STEP, DRAW, FOR, FROM,
    PARAM,
    SEMICO, L_BRACKET, R_BRACKET, COMMA,
    PLUS, MINUS, MUL, DIV, POWER,
    FUNC,
    CONST_ID,
    NON_TOKEN,
    ERROR_TOKEN
}

/**
 * 构造函数中的成员(带下划线的)全部都是protected，它们只能在
 * Token和它的子类中才能访问到。而不带下划线的属性在Token里
 * 没有，只在它的子类中才有。这是因为，这些属性的存在就是为了
 * getter和setter。某些子类比如PI这种，只有getter不允许有
 * setter，所以getter和setter的实现权力要完全移交给子类才可以。
 */
open class Token {
    protected open var _type: TokenType = TokenType.NULL
    protected open var _originStr: String = ""
    protected open var _value: Double = Double.NaN

    /**
     * 所有token的type和原始字符串都可以给getter，
     * 所以写到父类中。
     */
    open val type get() = _type
    open val originStr get() = _originStr
}