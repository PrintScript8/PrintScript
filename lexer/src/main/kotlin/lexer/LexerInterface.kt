package lexer

import token.TokenInterface

interface LexerInterface {
    fun tokenize(input: String): List<TokenInterface>
    fun iterator(input: String): Iterator<TokenInterface>
}
