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

class DeclarationStrategy(private val types: Array<PrimType>) : ParseStrategy {

    companion object {
        private const val INDEX_JUMP = 3
    }

    override fun parse(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        if (isDeclaration(tokens, currentIndex)) {
            val modifier: ModifierType = statementNodes[statementNodes.lastIndex] as ModifierType
            val identifierToken = tokens[currentIndex]
            val typeToken = tokens[currentIndex + 2]
            val declarationNode = DeclarationType(
                modifier, IdentifierType(getPrim(typeToken, types)), identifierToken.text
            )
            statementNodes.add(declarationNode)
            return currentIndex + INDEX_JUMP
        } else {
            statementNodes.add(VariableType(tokens[currentIndex].text, null, false))
            return currentIndex + (INDEX_JUMP - 2)
        }
    }

    private fun isDeclaration(tokens: List<Token>, currentIndex: Int): Boolean {
        if (currentIndex > 0 && tokens[currentIndex - 1].type == Modifier) {
            if (currentIndex + 1 < tokens.size) {
                require(tokens[currentIndex + 1].type == Declaration) { "Missing ':' in the declaration" }
                return true
            }
        }
        return false
    }

    private fun getPrim(token: Token, types: Array<PrimType>): PrimType {
        return types.find { it.name.lowercase() == token.text.lowercase() }
            ?: throw IllegalArgumentException("Trying to declare an invalid type")
    }
}
