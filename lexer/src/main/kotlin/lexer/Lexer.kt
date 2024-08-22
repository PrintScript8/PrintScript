package org.example.lexer

import org.example.token.Token
import java.io.File

interface Lexer {

    fun setFile(input: File)
    fun getTokens(): List<Token>
}
