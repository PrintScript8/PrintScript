package org.example

import org.example.lexer.LexerImplementation
import java.io.File

fun main() {
    val lexer = LexerImplementation()
    lexer.setFile(File("C:\\Users\\hilul\\projects\\ingSis\\PrintScript\\src\\main\\kotlin\\sample.txt"))
    lexer.getTokens();
}