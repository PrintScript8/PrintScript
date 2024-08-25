package parser.strategies

import node.dynamic.*
import token.*
import type.LiteralType
import type.LiteralValue
import java.util.*

class RightSideParser {

    fun parseRightHandSide(tokens: List<Token>, startIndex: Int, endTokenType: TokenType): Pair<DynamicNode, Int> {
        val tuple: Pair<Queue<Token>, Int> = buildQueue(tokens, startIndex, endTokenType)
        val expressionQueue: Queue<Token> = tuple.first
        val index: Int = tuple.second
        val opStack: Stack<DynamicNode> = Stack()
        if (expressionQueue.isEmpty()) throw IllegalArgumentException("Missing assignee in assignment! at ${tokens[index].position}")

        while (expressionQueue.isNotEmpty()) {
            val currentToken: Token = expressionQueue.remove()
            when (currentToken.type) {
                NumberLiteral -> {
                    val node = LiteralType(LiteralValue.NumberValue(currentToken.text.toDouble()))
                    opStack.add(node)
                }
                StringLiteral -> {
                    val node = LiteralType(LiteralValue.StringValue(currentToken.text))
                    opStack.add(node)
                }
                Identifier -> {
                    val node = VariableType(currentToken.text, null, false)
                    opStack.add(node)
                }
                Multiply -> {
                    val node = parseBinaryOperation(opStack) { left, right -> MultiplyType(left, right, null) }
                    opStack.add(node)
                }
                Divide -> {
                    val node = parseBinaryOperation(opStack) { left, right -> DivisionType(left, right, null) }
                    opStack.add(node)
                }
                Plus -> {
                    val node = parseBinaryOperation(opStack) { left, right -> SumType(left, right, null) }
                    opStack.add(node)
                }
                Minus -> {
                    val node = parseBinaryOperation(opStack) { left, right -> SubtractType(left, right, null) }
                    opStack.add(node)
                }
                else -> throw IllegalArgumentException("Unexpected token type: ${currentToken.type} at ${tokens[index].position}")
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

        while (currentIndex < tokens.size && tokens[currentIndex].type != endTokenType) {
            val token = tokens[currentIndex]

            when (token.type) {
                NumberLiteral, StringLiteral, Identifier, CloseParenthesis -> {
                    queue.add(tokens[currentIndex])
                }
                CloseParenthesis -> {
                    while (stack.isNotEmpty()) {
                        val poppedToken = stack.pop()
                        if (poppedToken.type == OpenParenthesis) {
                            break
                        }
                        queue.add(poppedToken)
                        if (stack.isEmpty()) {
                            throw IllegalArgumentException("Missing opening parenthesis for ')' at ${tokens[currentIndex].position}")
                        }
                    }
                }
                Multiply, Divide -> {
                    stack.add(tokens[currentIndex])
                }
                Plus, Minus -> {
                    if (stack.isEmpty() || ((stack.peek().type != Multiply) && (stack.peek().type != Divide))) {
                        stack.add(tokens[currentIndex])
                    } else {
                        queue.add(stack.pop())
                        stack.add(tokens[currentIndex])
                    }
                }
                else -> throw IllegalArgumentException("Unexpected token type in queue builder: ${token.type} at at ${tokens[currentIndex].position}")
            }

            currentIndex++
        }
        while (stack.isNotEmpty()) {
            if (queue.peek().type == OpenParenthesis) {
                throw IllegalArgumentException("Parenthesis was opened and never closed at at ${tokens[currentIndex].position}")
            }
            queue.add(stack.pop())
        }
        return Pair(queue, currentIndex)
    }
}
