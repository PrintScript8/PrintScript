package parser.elements

import node.dynamic.DynamicNode
import node.staticpkg.StaticNode
import org.example.node.Node
import token.Ending
import token.Token

class Parser2 : Parser {
    private val tokenHandler = TokenHandler()

    override fun parse(tokens: List<Token>): List<StaticNode> {
        var i = 0
        val astList: MutableList<StaticNode> = mutableListOf()
        val statementNodes: MutableList<Node> = mutableListOf()

        while (i < tokens.size) {
            i = tokenHandler.handle(tokens, i, statementNodes)
            if (i >= tokens.size) {
                throw IllegalArgumentException("Expected ';' at end of statement. At: ${tokens[tokens.lastIndex].position}")
            } else {
                if (tokens[i].type == Ending) {
                    if (statementNodes.isNotEmpty()) {
                        for (node in statementNodes.asReversed()) {
                            if (node is StaticNode && node !is DynamicNode) {
                                astList.add(node)
                                break
                            }
                        }
                        statementNodes.clear()
                        i += 1
                    } else {
                        throw IllegalArgumentException("Didn't expect ';' At: ${tokens[i].position}")
                    }
                }
            }
        }
        return astList
    }
}