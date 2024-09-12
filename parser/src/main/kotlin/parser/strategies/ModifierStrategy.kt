package parser.strategies

import node.Node
import node.staticpkg.ModifierType
import token.Identifier
import token.TokenInterface

class ModifierStrategy(private val validMods: Set<String>) : ParseStrategy {


    override fun parse(
        tokenInterfaces: List<TokenInterface>,
        currentIndex: Int,
        statementNodes: MutableList<Node>
    ): Int {
        require(validMods.contains(tokenInterfaces[currentIndex].text)) {
            "Modifier ${tokenInterfaces[currentIndex].text} is not supported in this version at:" +
                    " ${tokenInterfaces[currentIndex].position}"
        }
        require(currentIndex + 1 < tokenInterfaces.size && tokenInterfaces[currentIndex+1].type == Identifier) {
            "Modifier must be used with a variable at: ${tokenInterfaces[currentIndex].position}"
        }
        statementNodes.add(parseModifier(tokenInterfaces, currentIndex))
        return currentIndex + 1
    }

    private fun parseModifier(tokenInterfaces: List<TokenInterface>, index: Int): ModifierType {
        val modifier: String = tokenInterfaces[index].text
        return ModifierType(modifier, modifier != "const")
    }
}
