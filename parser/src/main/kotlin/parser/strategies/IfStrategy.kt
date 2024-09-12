package parser.strategies

import node.Node
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.VariableType
import node.staticpkg.IfElseType
import node.staticpkg.StaticNode
import parser.elements.StatementParser
import parser.elements.TokenHandler
import token.Boolean
import token.CloseBrace
import token.CloseParenthesis
import token.Identifier
import token.OpenBrace
import token.OpenParenthesis
import token.TokenInterface

class IfStrategy : ParseStrategy {

    companion object {
        const val INDEX_JUMP_LOW = 2
        const val INDEX_JUMP_HIGH = 5
    }

    var tokenHandler: TokenHandler? = null

    override fun parse(
        tokenInterfaces: List<TokenInterface>,
        currentIndex: Int,
        statementNodes: MutableList<Node>
    ): Int {
        require(tokenHandler != null) { "Token Handler is not defined!!!" }
        val statementParser: StatementParser = StatementParser(tokenHandler!!)
        verifySyntax(currentIndex, tokenInterfaces)
        // ya puedo incrementar indice a +4
        val argumentToken: TokenInterface = tokenInterfaces[currentIndex + 2]
        val argument = if (argumentToken.type == Identifier) {
            VariableType(argumentToken.text, null)
        } else {
            LiteralType(LiteralValue.BooleanValue(argumentToken.text == "true"))
        }

        // El ultimo elemento de la lista es la } que representa que termino el if original
        // Por esto, lo unico que me interesa es lo que hay entre el indice despues del currentIndex+4 hasta last-1

        val lastIndex = findBraces(tokenInterfaces, INDEX_JUMP_HIGH)
        val trueBlock: List<StaticNode> = statementParser.parseStatement(
            createSubList(tokenInterfaces, INDEX_JUMP_HIGH, lastIndex)
        )
        val ifNode = IfElseType(trueBlock, argument, null)
        statementNodes.add(ifNode)
        return lastIndex
    }

    private fun findBraces(list: List<TokenInterface>, startingIndex: Int): Int {
        var level: Int = 1
        for (i in startingIndex until list.size) {
            val token = list[i]
            if (token.type is OpenBrace) level++
            if (token.type is CloseBrace) level--
            if (level == 0) return i
        }
        return list.size
    }

    private fun verifySyntax(currentIndex: Int, tokenInterfaces: List<TokenInterface>) {
        require(
            currentIndex + 1 < tokenInterfaces.size &&
                tokenInterfaces[currentIndex + 1].type == OpenParenthesis
        ) {
            "If missing '(' for condition at: ${tokenInterfaces[currentIndex].position}"
        }
        // ya puedo incrementar indice a +1
        require(
            currentIndex + 2 < tokenInterfaces.size &&
                (
                    tokenInterfaces[currentIndex + INDEX_JUMP_LOW].type == Identifier ||
                        tokenInterfaces[currentIndex + INDEX_JUMP_LOW].type == Boolean
                    )
        ) {
            "Invalid argument ${tokenInterfaces[currentIndex + INDEX_JUMP_LOW].type} passed to if statement at: " +
                "${tokenInterfaces[currentIndex + INDEX_JUMP_LOW].position}"
        }
        require(
            currentIndex + INDEX_JUMP_LOW + 1 < tokenInterfaces.size &&
                tokenInterfaces[currentIndex + INDEX_JUMP_LOW + 1].type == CloseParenthesis
        ) {
            "If missing ')' for condition at: ${tokenInterfaces[currentIndex + INDEX_JUMP_LOW].position}"
        }
        require(
            currentIndex + INDEX_JUMP_HIGH - 1 < tokenInterfaces.size &&
                tokenInterfaces[currentIndex + INDEX_JUMP_HIGH - 1].type == OpenBrace
        ) {
            "If missing '{' for condition at: ${tokenInterfaces[currentIndex + INDEX_JUMP_LOW + 1].position}"
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

// LA IDEA ES QUE LA REGLA DEL IF MANDE A PARSEAR DE A UNA SUS EXPRESIONES Y SE LAS GUARDE EN LA LISTA
// DEL LADO QUE CORRESPONDE.
// EN CASO DE ENCONTRARSE UN IF ADENTRO LLAMA RECURSIVAMENTE A SU MISMA REGLA
