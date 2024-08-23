package parser.strategies

import node.dynamic.*
import org.example.token.Token
import org.example.token.TokenType
import type.LiteralType
import type.LiteralValue
import java.util.*

class RightSideParser {

    fun parseRightHandSide(tokens: List<Token>, startIndex: Int, endTokenType: TokenType): Pair<DynamicNode, Int> {
        val tuple: Pair<Queue<Token>, Int> = buildQueue(tokens, startIndex, endTokenType)
        val expressionQueue: Queue<Token> = tuple.first
        val index: Int = tuple.second
        val opStack: Stack<DynamicNode> = Stack()

        while (expressionQueue.isNotEmpty()) {
            val currentToken: Token = expressionQueue.remove()
            when (currentToken.getType()) {
                TokenType.NUMBER_LITERAL -> {
                    val node = LiteralType(LiteralValue.NumberValue(currentToken.getString().toDouble()))
                    opStack.add(node)
                }
                TokenType.STRING_LITERAL -> {
                    val node = LiteralType(LiteralValue.StringValue(currentToken.getString()))
                    opStack.add(node)
                }
                TokenType.IDENTIFIER_VAR -> {
                    val node = VariableType(currentToken.getString(), null, false)
                    opStack.add(node)
                }
                TokenType.MULT -> {
                    val node = parseBinaryOperation(opStack) { left, right -> MultiplyType(left, right, null) }
                    opStack.add(node)
                }
                TokenType.DIV -> {
                    val node = parseBinaryOperation(opStack) { left, right -> DivisionType(left, right, null) }
                    opStack.add(node)
                }
                TokenType.SUM -> {
                    val node = parseBinaryOperation(opStack) { left, right -> SumType(left, right, null) }
                    opStack.add(node)
                }
                TokenType.SUB -> {
                    val node = parseBinaryOperation(opStack) { left, right -> SubtractType(left, right, null) }
                    opStack.add(node)
                }
                else -> throw IllegalArgumentException("Unexpected token type: ${currentToken.getType()}")
            }
        }
        return Pair(opStack.pop(), index)
    }

    private fun parseBinaryOperation(
        opStack: Stack<DynamicNode>,
        operation: (DynamicNode, DynamicNode) -> DynamicNode
    ): DynamicNode {
        if (opStack.size < 2) throw IllegalArgumentException("Operation is missing arguments")
        val rightNode = opStack.pop()
        val leftNode = opStack.pop()
        return operation(leftNode, rightNode)
    }

    private fun buildQueue(tokens: List<Token>, startIndex: Int, endTokenType: TokenType): Pair<Queue<Token>, Int> {
        var currentIndex = startIndex
        val stack: Stack<Token> = Stack()
        val queue: Queue<Token> = LinkedList()

        while (currentIndex < tokens.size && tokens[currentIndex].getType() != endTokenType) {
            val token = tokens[currentIndex]

            when (token.getType()) {
                TokenType.NUMBER_LITERAL, TokenType.STRING_LITERAL, TokenType.IDENTIFIER_VAR, TokenType.PARENTHESIS_OPEN -> {
                    queue.add(tokens[currentIndex])
                }
                TokenType.PARENTHESIS_CLOSE -> {
                    while (stack.isNotEmpty()) {
                        val poppedToken = stack.pop()
                        if (poppedToken.getType() == TokenType.PARENTHESIS_OPEN) {
                            break
                        }
                        queue.add(poppedToken)
                        if (stack.isEmpty()) {
                            throw IllegalArgumentException("Missing opening parenthesis for ')'")
                        }
                    }
                }
                TokenType.MULT, TokenType.DIV -> {
                    stack.add(tokens[currentIndex])
                }
                TokenType.SUM, TokenType.SUB -> {
                    if (stack.isEmpty() || ((stack.peek().getType() != TokenType.MULT) && (stack.peek().getType() != TokenType.DIV))) {
                        stack.add(tokens[currentIndex])
                    } else {
                        queue.add(stack.pop())
                        stack.add(tokens[currentIndex])
                    }
                }
                else -> throw IllegalArgumentException("Unexpected token type in queue builder: ${token.getType()}")
            }

            currentIndex++
        }
        while (stack.isNotEmpty()) {
            if (queue.peek().getType() == TokenType.PARENTHESIS_OPEN) {
                throw IllegalArgumentException("Parenthesis was opened and never closed")
            }
            queue.add(stack.pop())
        }
        return Pair(queue, currentIndex)
    }
}
