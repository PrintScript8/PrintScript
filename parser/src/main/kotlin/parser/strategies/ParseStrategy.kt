package parser.strategies

import node.Node
import token.Token

interface ParseStrategy {
    fun parse(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int
}
