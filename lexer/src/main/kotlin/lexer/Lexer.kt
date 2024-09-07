package lexer

import token.TokenInterface

interface Lexer {
    fun tokenize(input: String): List<TokenInterface>
    fun iterator(input: String): Iterator<TokenInterface>
}
