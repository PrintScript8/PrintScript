package parser.strategies

import node.Node
import node.staticpkg.ModifierType
import token.TokenInterface

class ModifierStrategy : ParseStrategy {

    // TO DO: Hacer que solo version 1.1 permita const y la otra solo let
    override fun parse(
        tokenInterfaces: List<TokenInterface>,
        currentIndex: Int,
        statementNodes: MutableList<Node>
    ): Int {
        statementNodes.add(parseModifier(tokenInterfaces, currentIndex))
        return currentIndex + 1
    }

    private fun parseModifier(tokenInterfaces: List<TokenInterface>, index: Int): ModifierType {
        val modifier: String = tokenInterfaces[index].text
        return ModifierType(modifier, modifier != "const")
    }
}
