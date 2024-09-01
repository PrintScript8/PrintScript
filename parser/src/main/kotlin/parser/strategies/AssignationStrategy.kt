package parser.strategies

import elements.RightSideParser
import node.Node
import node.dynamic.VariableType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import token.Ending
import token.TokenInterface

class AssignationStrategy : ParseStrategy {

    private val rightSideParser: RightSideParser = RightSideParser()

    override fun parse(
        tokenInterfaces: List<TokenInterface>,
        currentIndex: Int,
        statementNodes: MutableList<Node>
    ): Int {
        require(statementNodes.isNotEmpty()) {
            "'=' cannot be used alone, missing previous argument at: ${tokenInterfaces[currentIndex].position}"
        }
        return when (val lastNode = statementNodes.last()) {
            is VariableType -> parseExpression(tokenInterfaces, currentIndex, statementNodes)
            is DeclarationType -> parseAssignation(tokenInterfaces, currentIndex, statementNodes)
            else -> throw IllegalArgumentException(
                "'=' cannot be used with first argument " +
                    "${lastNode.javaClass} at: " +
                    "${tokenInterfaces[currentIndex].position}"
            )
        }
    }

    private fun parseExpression(
        tokenInterfaces: List<TokenInterface>,
        currentIndex: Int,
        statementNodes: MutableList<Node>
    ): Int {
        val variableNode = statementNodes.last() as VariableType
        val (rightHandSideNode, nextIndex) =
            rightSideParser.parseRightHandSide(tokenInterfaces, currentIndex + 1, Ending)
        val expressionNode = ExpressionType(variableNode, rightHandSideNode)
        statementNodes.add(expressionNode)
        return nextIndex
    }

    private fun parseAssignation(
        tokenInterfaces: List<TokenInterface>,
        currentIndex: Int,
        statementNodes: MutableList<Node>
    ): Int {
        val declarationNode = statementNodes.last() as DeclarationType
        val (rightHandSideNode, nextIndex) =
            rightSideParser.parseRightHandSide(tokenInterfaces, currentIndex + 1, Ending)
        val assignationNode = AssignationType(declarationNode, rightHandSideNode)
        statementNodes.add(assignationNode)
        return nextIndex
    }
}
