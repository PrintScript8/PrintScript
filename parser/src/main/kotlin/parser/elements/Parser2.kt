// Parser2.kt
package parser.elements

import node.Node
import node.staticpkg.StaticNode
import token.Ending
import token.TokenInterface

class Parser2(private val tokenHandler: TokenHandler) : Parser {

    override fun parse(tokenInterfaces: List<TokenInterface>): List<StaticNode> {
        var i = 0
        val astList: MutableList<StaticNode> = mutableListOf()
        val statementNodes: MutableList<Node> = mutableListOf()

        while (i < tokenInterfaces.size) {
            i = tokenHandler.handle(tokenInterfaces, i, statementNodes)
            require(i < tokenInterfaces.size) {
                "Expected ';' at end of statement. At: " +
                    "${tokenInterfaces[tokenInterfaces.lastIndex].position}"
            }
            i = handleEnding(tokenInterfaces, i, statementNodes, astList)
        }
        return astList
    }

    private fun handleEnding(
        tokenInterfaces: List<TokenInterface>,
        i: Int,
        statementNodes: MutableList<Node>,
        astList: MutableList<StaticNode>
    ): Int {
        var i1 = i
        if (tokenInterfaces[i1].type == Ending) {
            require(statementNodes.isNotEmpty()) {
                "Didn't expect ';' At: ${tokenInterfaces[i1].position}"
            }
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
                break
            }
        }
    }
}
