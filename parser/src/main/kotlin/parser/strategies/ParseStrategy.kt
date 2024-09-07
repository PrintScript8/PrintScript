package parser.strategies

import node.Node
import token.TokenInterface

interface ParseStrategy {
    fun parse(tokenInterfaces: List<TokenInterface>, currentIndex: Int, statementNodes: MutableList<Node>): Int
}
