package parser.strategies

import node.staticpkg.ModifierType
import org.example.node.Node
import org.example.token.Token

class ModifierStrategy: ParseStrategy {

    override fun parse(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        statementNodes.add(parseModifier(tokens, currentIndex))
        return currentIndex + 1
    }
    
    private fun parseModifier(tokens: List<Token>, index: Int): ModifierType{
        val modifier: String = tokens[index].getString()
        return ModifierType(modifier, modifier != "const")
    }
}