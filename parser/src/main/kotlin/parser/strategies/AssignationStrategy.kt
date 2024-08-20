package parser.strategies

import node.dynamic.*
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import org.example.node.Node
import org.example.token.Token
import org.example.token.TokenType
import type.LiteralType
import type.LiteralValue

class AssignationStrategy : ParseStrategy {

    override fun parse(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        if (statementNodes.isEmpty()) {
            throw IllegalArgumentException("'=' cannot be used alone, missing previous argument")
        }
        return when (val lastNode: Node = statementNodes[statementNodes.lastIndex]) {
            is VariableType -> parseExpression(tokens, currentIndex, statementNodes)
            is DeclarationType -> parseAssignation(tokens, currentIndex, statementNodes)
            else -> throw IllegalArgumentException("'=' cannot be used with first argument ${lastNode.javaClass}")
        }
    }

    private fun parseExpression(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        val variableNode = statementNodes[statementNodes.lastIndex] as VariableType
        val rightNode = parseRightHandSide(tokens, currentIndex + 1)
        val expressionNode = ExpressionType(variableNode, rightNode)
        // La idea es que aca tome los dos valores del par devuelto para usarlos
        statementNodes.add(expressionNode)
        // aca se supone que debo devolver el indice corriendolo hasta la cantidad que se movio tras leer el lado derecho completo
        return currentIndex + rightNode.tokenCount() + 1
    }

    private fun parseAssignation(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        val declarationNode = statementNodes[statementNodes.lastIndex] as DeclarationType
        val rightNode = parseRightHandSide(tokens, currentIndex + 1)
        val assignationNode = AssignationType(declarationNode, rightNode)
        statementNodes.add(assignationNode)
        // aca se supone que debo devolver el indice corriendolo hasta la cantidad que se movio tras leer el lado derecho completo
        return currentIndex + rightNode.tokenCount() + 1
    }


    // Considerar sacar esto a una clase con la logica de parsear nodos del lado derecho (Nodos dinamicos)
    private fun parseRightHandSide(tokens: List<Token>, startIndex: Int): Pair<DynamicNode, Int> {
        var currentIndex = startIndex
        var leftNode: DynamicNode? = null
        var currentOperator: String? = null

        while (currentIndex < tokens.size && tokens[currentIndex].getType() != TokenType.ENDING) {
            val token = tokens[currentIndex]

            when (token.getType()) {
                TokenType.NUMBER_LITERAL -> {
                    val literalNode = LiteralType(LiteralValue.NumberValue(token.getString().toDouble()))
                    leftNode = combineNodes(leftNode, literalNode, currentOperator)
                }
                TokenType.STRING_LITERAL -> {
                    val literalNode = LiteralType(LiteralValue.StringValue(token.getString()))
                    leftNode = combineNodes(leftNode, literalNode, currentOperator)
                }
                TokenType.IDENTIFIER_VAR -> {
                    val variableNode = VariableType(token.getString(), null, false)
                    leftNode = combineNodes(leftNode, variableNode, currentOperator)
                }
                TokenType.OPERAND -> {
                    if (leftNode == null) {
                        throw IllegalArgumentException("Operator '${token.getString()}' used without left operand")
                    }
                    currentOperator = token.getString()
                }
                else -> throw IllegalArgumentException("Unexpected token type: ${token.getType()}")
            }

            currentIndex++
        }

        return leftNode ?: throw IllegalArgumentException("Expression expected after '='")
    }

    private fun combineNodes(leftNode: DynamicNode?, rightNode: DynamicNode, operator: String?): DynamicNode {
        return if (leftNode == null) {
            rightNode
        } else {
            when (operator) {
                "+" -> SumType(leftNode, rightNode, null)
                "-" -> SubtractType(leftNode, rightNode, null)
                "*" -> MultiplyType(leftNode, rightNode, null)
                "/" -> DivisionType(leftNode, rightNode, null)
                else -> throw IllegalArgumentException("Unsupported operator or missing operator between operands")
            }
        }
    }


    /*
    La idea seria aca que, si no hay un leftNode, lo setea.
    En caso de que haya, trata de convinar el que tiene con el left, cosa que solo se puede con operaciones.
    Esto nos restringe para no poder poner dos numeros seguidos ni un operador suelto sin nada antes.

    Habria que ver que hacer con el nodo derecho si el nodo izquierdo que ya existe tiene la posibilidad de tener un nodo derecho que no tiene.
    Pensando en esto, "leftNode" se podria entender como previous node.
     */

}
