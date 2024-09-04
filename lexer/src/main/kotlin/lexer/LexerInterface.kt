package lexer

import token.TokenInterface
import java.io.FileInputStream

interface LexerInterface {
    fun tokenize(input: FileInputStream): List<TokenInterface>
}
