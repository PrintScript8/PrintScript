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

class DeclarationStrategy : ParseStrategy {

    companion object {
        private const val INDEX_JUMP = 3
    }

    override fun parse(
        tokenInterfaces: List<TokenInterface>,
        currentIndex: Int,
        statementNodes: MutableList<Node>
    ): Int {
        if (isDeclaration(tokenInterfaces, currentIndex)) {
            val modifier: ModifierType = statementNodes[statementNodes.lastIndex] as ModifierType
            val identifierToken = tokenInterfaces[currentIndex]
            val typeToken = tokenInterfaces[currentIndex + 2]
            val declarationNode = DeclarationType(modifier, IdentifierType(getPrim(typeToken)), identifierToken.text)
            statementNodes.add(declarationNode)
            return currentIndex + INDEX_JUMP
        } else {
            statementNodes.add(VariableType(tokenInterfaces[currentIndex].text, null, false))
            return currentIndex + (INDEX_JUMP - 2)
        }
    }

    private fun isDeclaration(tokenInterfaces: List<TokenInterface>, currentIndex: Int): Boolean {
        if (currentIndex > 0 && tokenInterfaces[currentIndex - 1].type == Modifier) {
            if (currentIndex + 1 < tokenInterfaces.size) {
                require(tokenInterfaces[currentIndex + 1].type == Declaration) {
                    "Missing ':' in the declaration"
                }
                return true
            }
        }
        return false
    }

    private fun getPrim(tokenInterface: TokenInterface): PrimType {
        return if (tokenInterface.text == "String") {
            PrimType.STRING
        } else {
            PrimType.NUMBER
        }
    }
}
