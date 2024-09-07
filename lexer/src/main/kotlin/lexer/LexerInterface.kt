package lexer

import token.TokenInterface

interface LexerInterface {
    fun iterator(): Iterator<TokenInterface>
}
