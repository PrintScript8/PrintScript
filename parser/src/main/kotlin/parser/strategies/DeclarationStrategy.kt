package parser.strategies

import node.Node
import node.PrimType
import node.dynamic.VariableType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import token.Declaration
import token.Modifier
import token.Token

class DeclarationStrategy : ParseStrategy {

    companion object {
        private const val INDEX_JUMP = 3
    }

    override fun parse(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        // Verifica si el token actual representa una declaración
        if (isDeclaration(tokens, currentIndex)) {
            // Busca el modificador creado anteriormente
            val modifier: ModifierType = statementNodes[statementNodes.lastIndex] as ModifierType
            // Busca el valor/nombre de la variable
            val identifierToken = tokens[currentIndex]
            // Obtiene el token del tipo de dato que sigue al identificador
            val typeToken = tokens[currentIndex + 2]
            // Crea un nodo de declaración con el modificador, identificador y tipo
            val declarationNode = DeclarationType(modifier, IdentifierType(getPrim(typeToken)), identifierToken.text)
            // Agrega el nodo de declaración a la lista de nodos
            statementNodes.add(declarationNode)
            // Avanza el índice más allá del identificador, el ":" y el tipo
            return currentIndex + INDEX_JUMP
        } else {
            // Si no es una declarasion agrega nodo de variable
            statementNodes.add(VariableType(tokens[currentIndex].text, null, false))
            return currentIndex + (INDEX_JUMP - 2)
        }
    }

    private fun isDeclaration(tokens: List<Token>, currentIndex: Int): Boolean {
        // Verifica si el token anterior es un modificador como 'let'
        if (currentIndex > 0 && tokens[currentIndex - 1].type == Modifier) {
            // Verifica si el próximo token es un colon, indicando que es una declaración
            if (currentIndex + 1 < tokens.size) {
                require(tokens[currentIndex + 1].type == Declaration) { "Missing ':' in the declaration" }
                return true
            }
        }
        return false
    }

    private fun getPrim(token: Token): PrimType {
        return if (token.text == "String") {
            PrimType.STRING
        } else {
            PrimType.NUMBER
        }
    }
}
