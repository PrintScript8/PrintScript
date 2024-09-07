package parser.elements

import node.Node
import node.staticpkg.StaticNode
import token.Ending
import token.Token

class Parser(private val tokenHandler: TokenHandler, private val iterator: Iterator<Token>) : ParserInterface {

    // Este metodo parse ahora usará el iterador que se pasa al constructor
    // No necesita recibir el iterador como parámetro
    override fun parse(): List<StaticNode> {
        val astList = mutableListOf<StaticNode>()
        val statementTokens = mutableListOf<Token>()
        var currentToken: Token? = null

        // Se usa el iterador directamente desde el campo 'iterator'
        while (iterator.hasNext()) {
            currentToken = iterator.next()
            statementTokens.add(currentToken)
            if (currentToken.type == Ending) {
                // Lógica de parseo
                astList.addAll(parseStatement(statementTokens)) // El parseStatement se encarga del parseo del statement
                statementTokens.clear()
            }
        }

        require(currentToken != null) { "No tokens received to parse" }
        require(statementTokens.isEmpty()) { "Missing ';' at: ${currentToken.position}" }
        return astList
    }

    // Met odo que devuelve un iterador para el AST construido
    override fun iterator(): Iterator<StaticNode> {
        return object : Iterator<StaticNode> {
            private val astList = parse() // Usa el met odo parse sin pasar tokens
            private var currentIndex = 0

            override fun hasNext(): Boolean {
                return currentIndex < astList.size
            }

            override fun next(): StaticNode {
                if (!hasNext()) throw NoSuchElementException()
                return astList[currentIndex++]
            }
        }
    }

    // Met odo privado que parsea un statement
    // Se renombra para mayor claridad: parse -> parseStatement
    private fun parseStatement(tokens: List<Token>): List<StaticNode> {
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

    // Met odo para manejar el token de fin de statement
    private fun handleEnding(
        tokenInterfaces: List<Token>,
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

    // Met odo para agregar un StaticNode a la lista de AST
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
