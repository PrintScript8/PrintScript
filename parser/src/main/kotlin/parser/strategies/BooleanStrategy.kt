package parser.strategies

import node.Node
import node.dynamic.BooleanType
import node.dynamic.LiteralValue
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import token.Assignment
import token.Token

class BooleanStrategy : ParseStrategy {

    // Esta estrategia tiene de malo que se encarga de el assignation cuando no deberia

    override fun parse(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        val booleanNode: BooleanType = parseBoolean(tokens, currentIndex)
        statementNodes.add(booleanNode)
        if (tokens[currentIndex - 1].type == Assignment) {
            statementNodes.add(generateAssignment(statementNodes, booleanNode))
        }
        return currentIndex + 1
    }

    private fun parseBoolean(tokens: List<Token>, index: Int): BooleanType {
        return if (tokens[index].text == "true") {
            BooleanType(LiteralValue.StringValue("true"))
        } else {
            BooleanType(LiteralValue.StringValue("false"))
        }
    }

    private fun generateAssignment(statementNodes: MutableList<Node>, booleanNode: BooleanType): AssignationType {
        return AssignationType(statementNodes[statementNodes.lastIndex - 1] as DeclarationType, booleanNode)
    }
}
