package org.example

import org.example.lexer.LexerImplementation
import org.example.token.Token
import java.io.File

fun main() {
    val lexer = LexerImplementation()
    lexer.setFile(File("C:\\Users\\hilul\\projects\\ingSis\\PrintScript\\src\\test\\testfiles\\splittingRules.txt"))
    val tokens: List<Token> = lexer.getTokens();
    for (token: Token in tokens) {
        println(token);
    }
}