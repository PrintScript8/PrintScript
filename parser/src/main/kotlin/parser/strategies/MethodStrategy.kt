package parser.strategies

import node.dynamic.*
import node.staticpkg.PrintLnType
import org.example.node.Node
import org.example.token.Token
import org.example.token.TokenType
import type.LiteralType
import type.LiteralValue

class MethodStrategy : ParseStrategy {

    override fun parse(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        if(statementNodes.isNotEmpty()){
            throw IllegalArgumentException("Can't call nativeMethod '${tokens[currentIndex].getString()}' with other arguments before it")
        }
        else{
            if(currentIndex + 1 >= tokens.size || tokens[currentIndex+1].getType() == TokenType.PARENTHESIS){
                throw IllegalArgumentException("Expected arguments for method")
            }
            else{
                val tuple: Pair<DynamicNode, Int> = parseRightHandSide(tokens, currentIndex + 1)
                val argument: DynamicNode = tuple.first
                val resultIndex: Int = tuple.second
                statementNodes.add(PrintLnType(argument))
                return resultIndex
            }
        }
    }


    // Considerar sacar esto a una clase con la logica de parsear nodos del lado derecho (Nodos dinamicos)
    // NO ES EL MISMO QUE EL DE ASSIGNATION!!! ESTE FRENA EN PARENTESIS NO EN ;
    private fun parseRightHandSide(tokens: List<Token>, startIndex: Int): Pair<DynamicNode, Int> {
        var currentIndex = startIndex
        var leftNode: DynamicNode? = null
        var currentOperator: String? = null

        while (currentIndex < tokens.size && tokens[currentIndex].getType() != TokenType.PARENTHESIS) {
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
                else -> throw IllegalArgumentException("Unexpected token type in assignment: ${token.getType()}")
            }

            currentIndex++
        }
        if (leftNode == null) {
            throw IllegalArgumentException("Expression expected after '='")
        }
        return Pair(leftNode, currentIndex)
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

}