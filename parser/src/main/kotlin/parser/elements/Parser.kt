package parser.elements

import node.Node
import node.staticpkg.StaticNode
import token.Ending
import token.TokenInterface

class Parser(private val tokenHandler: TokenHandler, private val iterator: Iterator<TokenInterface>) : ParserInterface {

    // Este metodo parse ahora procesará un solo statement
    override fun parse(): StaticNode? {
        val statementTokens = mutableListOf<TokenInterface>()
        var currentToken: TokenInterface? = null

        // Se usa el iterador directamente desde el campo 'iterator'
        while (iterator.hasNext()) {
            currentToken = iterator.next()
            statementTokens.add(currentToken)
            if (currentToken.type == Ending) {
                // Lógica de parseo
                val statementNodes = parseStatement(statementTokens)
                statementTokens.clear()
                return statementNodes.firstOrNull() // Devuelve el primer StaticNode del statement
            }
        }
        return if (currentToken == null) {
            null
        } else {
            require(statementTokens.isEmpty()) { "Missing ';' at: ${currentToken.position}" }
            null
        }
    }

    override fun iterator(): Iterator<StaticNode> {
        return object : Iterator<StaticNode> {
            private var nextNode: StaticNode? = null

            override fun hasNext(): Boolean {
                if (nextNode == null) {
                    nextNode = parse()
                }
                return nextNode != null
            }

            override fun next(): StaticNode {
                if (!hasNext()) throw NoSuchElementException()
                val currentNode = nextNode
                nextNode = null
                return currentNode!!
            }
        }
    }

    private fun parseStatement(tokens: List<TokenInterface>): List<StaticNode> {
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
        for (i in statementNodes.size - 1 downTo 0) {
            val node = statementNodes[i]
            if (node is StaticNode) {
                astList.add(node)
                return
            }
        }
    }
}
