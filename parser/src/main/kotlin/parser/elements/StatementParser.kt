package parser.elements

import node.Node
import node.staticpkg.StaticNode
import token.CloseBrace
import token.Else
import token.Ending
import token.TokenInterface

class StatementParser(private val tokenHandler: TokenHandler) {

     fun parseStatement(tokens: List<TokenInterface>): List<StaticNode> {
        var i = 0
        val astList: MutableList<StaticNode> = mutableListOf()
        val statementNodes: MutableList<Node> = mutableListOf()

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
            require(statementNodes.isNotEmpty()) {
                "Didn't expect ';' At: ${tokenInterfaces[j].position}"
            }
            addStaticNodeToAstList(statementNodes, astList)
            statementNodes.clear()
            j += 1
        }
        else if (tokenInterfaces[j].type == CloseBrace){
            if(j+1 < tokenInterfaces.size && tokenInterfaces[j+1].type == Else){
                val subStatementNodes = parseStatement(createSubList(tokenInterfaces, j+1, tokenInterfaces.lastIndex))
                addStaticNodeToAstList(subStatementNodes.toMutableList(), astList)
                j = tokenInterfaces.lastIndex
            }
            else{
                addStaticNodeToAstList(statementNodes, astList)
                j += 1
            }
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

    private fun <T> createSubList(originalList: List<T>, startIndex: Int, endIndex: Int): List<T> {
        require(startIndex in 0..originalList.size) { "Start index out of bounds" }
        require(endIndex in startIndex..originalList.size) { "End index out of bounds" }
        val newList = mutableListOf<T>()
        for (i in startIndex until endIndex) {
            newList.add(originalList[i])
        }
        return newList
    }
}
