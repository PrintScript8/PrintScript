package parser.elements

import node.Node
import parser.strategies.ParseStrategy
import token.Token
import token.TokenType

class TokenHandler(private val strategies: Map<TokenType, ParseStrategy>) {

    fun handle(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        val tokenType = tokens[currentIndex].type
        val strategy = strategies[tokenType]
        return strategy?.parse(tokens, currentIndex, statementNodes) ?: (currentIndex + 1)
    }
}
