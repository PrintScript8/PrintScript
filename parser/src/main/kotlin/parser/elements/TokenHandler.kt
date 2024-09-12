package parser.elements

import node.Node
import parser.strategies.ElseStrategy
import parser.strategies.IfStrategy
import parser.strategies.ParseStrategy
import token.TokenInterface
import token.TokenType

class TokenHandler(private val strategies: Map<TokenType, ParseStrategy>) {

    fun handle(tokenInterfaces: List<TokenInterface>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        val tokenType = tokenInterfaces[currentIndex].type
        val strategy = strategies[tokenType]
        if (strategy is IfStrategy) strategy.tokenHandler = this
        if (strategy is ElseStrategy) strategy.tokenHandler = this
        return strategy?.parse(tokenInterfaces, currentIndex, statementNodes) ?: (currentIndex + 1)
    }
}
