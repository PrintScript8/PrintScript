package parser.strategies

import node.Node
import node.PrimType
import node.dynamic.VariableType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import token.Declaration
import token.Modifier
import token.TokenInterface

class DeclarationStrategy(private val types: Array<PrimType>) : ParseStrategy {

    companion object {
        private const val INDEX_JUMP = 3
    }

    override fun parse(tokenInterfaces: List<TokenInterface>, currentIndex: Int, statementNodes: MutableList<Node>):
        Int {
        if (isDeclaration(tokenInterfaces, currentIndex)) {
            // In this branch a declaration node is parsed
            val modifier: ModifierType = statementNodes[statementNodes.lastIndex] as ModifierType
            val identifierToken = tokenInterfaces[currentIndex]
            val typeToken = tokenInterfaces[currentIndex + 2]
            val declarationNode = DeclarationType(
                modifier, IdentifierType(getPrim(typeToken, types)), identifierToken.text
            )
            statementNodes.add(declarationNode)
            return currentIndex + INDEX_JUMP
        } else {
            // In this branch an identifier node is parsed instead of a declaration
            statementNodes.add(VariableType(tokenInterfaces[currentIndex].text, null))
            return currentIndex + (INDEX_JUMP - 2)
        }
    }

    private fun isDeclaration(tokens: List<TokenInterface>, currentIndex: Int): Boolean {
        if (currentIndex > 0 && tokens[currentIndex - 1].type == Modifier) {
            if (currentIndex + 1 < tokens.size) {
                require(tokens[currentIndex + 1].type == Declaration) { "Missing ':' in the declaration" }
                return true
            }
        }
        return false
    }

    private fun getPrim(token: TokenInterface, types: Array<PrimType>): PrimType {
        return types.find { it.name.lowercase() == token.text.lowercase() }
            ?: throw IllegalArgumentException("Trying to declare an invalid type")
    }
}
