package parser.strategies

import org.example.node.Node
import org.example.token.Token

interface ParseStrategy {
    fun parse(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int
}
