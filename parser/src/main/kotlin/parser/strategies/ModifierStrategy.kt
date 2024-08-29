package parser.strategies

import node.Node
import node.staticpkg.ModifierType
import token.Token

class ModifierStrategy : ParseStrategy {

    override fun parse(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        statementNodes.add(parseModifier(tokens, currentIndex))
        return currentIndex + 1
    }

    private fun parseModifier(tokens: List<Token>, index: Int): ModifierType {
        val modifier: String = tokens[index].text
        return ModifierType(modifier, modifier != "const")
    }
}
