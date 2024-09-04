package parser.strategies

import elements.RightSideParser
import node.Node
import node.dynamic.VariableType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import token.Ending
import token.Token
import token.TokenType

class AssignationStrategy(
    private val allowedTypes: Set<TokenType>
) : ParseStrategy {

    private val rightSideParser: RightSideParser = RightSideParser(allowedTypes) // Pasar los tipos permitidos

    override fun parse(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        require(statementNodes.isNotEmpty()) {
            "'=' cannot be used alone, missing previous argument at: ${tokens[currentIndex].position}"
        }
        return when (val lastNode = statementNodes.last()) {
            is VariableType -> parseExpression(tokens, currentIndex, statementNodes)
            is DeclarationType -> parseAssignation(tokens, currentIndex, statementNodes)
            else -> throw IllegalArgumentException(
                "'=' cannot be used with first argument ${lastNode.javaClass} at: ${tokens[currentIndex].position}"
            )
        }
    }

    private fun parseExpression(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        val variableNode = statementNodes.last() as VariableType
        val (rightHandSideNode, nextIndex) = rightSideParser.parseRightHandSide(tokens, currentIndex + 1, Ending)
        val expressionNode = ExpressionType(variableNode, rightHandSideNode)
        statementNodes.add(expressionNode)
        return nextIndex
    }

    private fun parseAssignation(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        val declarationNode = statementNodes.last() as DeclarationType
        val (rightHandSideNode, nextIndex) = rightSideParser.parseRightHandSide(tokens, currentIndex + 1, Ending)
        val assignationNode = AssignationType(declarationNode, rightHandSideNode)
        statementNodes.add(assignationNode)
        return nextIndex
    }
}
