package parser.elements

import node.Node
import node.staticpkg.StaticNode
import token.Ending
import token.TokenInterface

class StatementParser(private val tokenHandler: TokenHandler) {

     fun parseStatement(tokens: List<TokenInterface>): List<StaticNode> {
        var i = 0
        val astList: MutableList<StaticNode> = mutableListOf()
        val statementNodes: MutableList<Node> = mutableListOf()

        // todo: Usar este metodo para parsear los statements dentro de un if. La idea es que el if lo llame

        while (i < tokens.size) {
            i = tokenHandler.handle(tokens, i, statementNodes)
            require(i < tokens.size) {
                "Expected ';' at end of statement. At: " +
                        "${tokens[tokens.lastIndex].position}"
            }
            i = handleEnding(tokens, i, statementNodes, astList)
        }
        return astList
    }

    private fun handleEnding(
        tokenInterfaces: List<TokenInterface>,
        i: Int,
        statementNodes: MutableList<Node>,
        astList: MutableList<StaticNode>
    ): Int {
        var j = i
        if (tokenInterfaces[j].type == Ending) {
            // TODO: Revisar que Modifier no se pueda devolver solo
            require(statementNodes.isNotEmpty()) {
                "Didn't expect ';' At: ${tokenInterfaces[j].position}"
            }
            addStaticNodeToAstList(statementNodes, astList)
            statementNodes.clear()
            j += 1
        }
        return j
    }

    private fun addStaticNodeToAstList(
        statementNodes: MutableList<Node>,
        astList: MutableList<StaticNode>
    ) {
        for (i in statementNodes.size - 1 downTo 0) {
            val node = statementNodes[i]
            if (node is StaticNode) {
                astList.add(node)
                return
            }
        }
    }
}
