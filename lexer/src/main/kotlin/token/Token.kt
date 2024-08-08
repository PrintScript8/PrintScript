package org.example.token

interface Token {

    fun getType(): TokenType
    fun getString(): String
    fun getLine(): Int
}