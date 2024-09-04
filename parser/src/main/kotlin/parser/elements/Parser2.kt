// Parser2.kt
package parser.elements

import node.Node
import node.staticpkg.StaticNode
import token.Ending
import token.Token

class Parser2(private val tokenHandler: TokenHandler) : Parser {

    override fun parse(tokens: List<Token>): List<StaticNode> {
        var i = 0
        val astList: MutableList<StaticNode> = mutableListOf()
        val statementNodes: MutableList<Node> = mutableListOf()

        while (i < tokens.size) {
            i = tokenHandler.handle(tokens, i, statementNodes)
            require(i < tokens.size) { "Expected ';' at end of statement. At: ${tokens[tokens.lastIndex].position}" }
            i = handleEnding(tokens, i, statementNodes, astList)
        }
        return astList
    }

    private fun handleEnding(
        tokens: List<Token>,
        i: Int,
        statementNodes: MutableList<Node>,
        astList: MutableList<StaticNode>
    ): Int {
        var i1 = i
        if (tokens[i1].type == Ending) {
            require(statementNodes.isNotEmpty()) { "Didn't expect ';' At: ${tokens[i1].position}" }
            addStaticNodeToAstList(statementNodes, astList)
            statementNodes.clear()
            i1 += 1
        }
        return i1
    }

    private fun addStaticNodeToAstList(
        statementNodes: MutableList<Node>,
        astList: MutableList<StaticNode>
    ) {
        for (node in statementNodes.asReversed()) {
            if (node is StaticNode) {
                astList.add(node)
                return
            }
        }
    }
}
