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
        if (tokenInterfaces[j].type == Ending || tokenInterfaces[j].type == CloseBrace) {
            require(statementNodes.isNotEmpty()) {
                "Didn't expect ';' At: ${tokenInterfaces[j].position}"
            }
            addStaticNodeToAstList(statementNodes, astList)
            statementNodes.clear()
            j += 1
        }
        if (tokenInterfaces[j].type == CloseBrace){
            // Aca va la logica de else
            // LOGICA ELSE
            // YO PUEDO ENCARGARME DE HACER EL IF Y AGREGARLO AL STATEMENT NODES
            // INMEDIATAMENTE SEGUIDO A ESO SI HAY UN ELSE PARSEO EL ELSE SOLO SI EL ULTIMO NODO EN STATEMENT NODES ES UN IF!!!!!
            // SI NO ES UN IF TIRO ROR. SI ES UN IF LO SACO DE LA LISTA Y CREO UNO NUEVO IF AGREGANDOLE LAS COSAS DEL ELSE
            // UNA VEZ TERMINADO DE PARSEAR EL ELSE, PUEDO DEVOLVER EL NODO
            if(j+1 < tokenInterfaces.size && tokenInterfaces[j+1].type == Else){
                val subStatementNodes = parseStatement(createSubList(tokenInterfaces, j+1, tokenInterfaces.lastIndex))
                addStaticNodeToAstList(subStatementNodes.toMutableList(), astList)
                j = tokenInterfaces.lastIndex
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
