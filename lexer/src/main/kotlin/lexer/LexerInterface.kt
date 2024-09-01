package lexer

import token.TokenInterface
import java.io.InputStream

interface LexerInterface {
    fun tokenize(input: InputStream): List<TokenInterface>
}
