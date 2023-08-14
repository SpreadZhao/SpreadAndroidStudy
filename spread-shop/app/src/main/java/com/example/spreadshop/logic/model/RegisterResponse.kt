package com.example.spreadshop.logic.model

data class RegisterResponse(val register: Register, val success: Boolean)
data class Register(val username: String, val password: String, val message: String)
