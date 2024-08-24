package parser.strategies

import node.dynamic.*
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import org.example.node.Node
import token.Ending
import token.Token


class AssignationStrategy : ParseStrategy {

    private val rightSideParser:RightSideParser = RightSideParser()

    override fun parse(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        validatePreviousNode(statementNodes)
        return when (val lastNode = statementNodes.last()) {
            is VariableType -> parseExpression(tokens, currentIndex, statementNodes)
            is DeclarationType -> parseAssignation(tokens, currentIndex, statementNodes)
            else -> throw IllegalArgumentException("'=' cannot be used with first argument ${lastNode.javaClass}")
        }
    }

    private fun validatePreviousNode(statementNodes: MutableList<Node>) {
        if (statementNodes.isEmpty()) {
            throw IllegalArgumentException("'=' cannot be used alone, missing previous argument")
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
