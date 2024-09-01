package elements

import node.dynamic.DivisionType
import node.dynamic.DynamicNode
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.MultiplyType
import node.dynamic.SubtractType
import node.dynamic.SumType
import node.dynamic.VariableType
import token.CloseParenthesis
import token.Divide
import token.Identifier
import token.Minus
import token.Multiply
import token.NumberLiteral
import token.OpenParenthesis
import token.Plus
import token.StringLiteral
import token.TokenInterface
import token.TokenType
import java.util.LinkedList
import java.util.Queue
import java.util.Stack

class RightSideParser {

    fun parseRightHandSide(
        tokenInterfaces: List<TokenInterface>,
        startIndex: Int,
        endTokenType: TokenType
    ):
        Pair<DynamicNode, Int> {
        val tuple: Pair<Queue<TokenInterface>, Int> = buildQueue(tokenInterfaces, startIndex, endTokenType)
        val expressionQueue: Queue<TokenInterface> = tuple.first
        val index: Int = tuple.second
        val opStack: Stack<DynamicNode> = Stack()
        require(expressionQueue.isNotEmpty()) {
            "Missing assignee in assignment! at ${tokenInterfaces[index].position}"
        }

        while (expressionQueue.isNotEmpty()) {
            val currentTokenInterface: TokenInterface = expressionQueue.remove()
            when (currentTokenInterface.type) {
                NumberLiteral -> {
                    val node = LiteralType(LiteralValue.NumberValue(currentTokenInterface.text.toDouble()))
                    opStack.add(node)
                }
                StringLiteral -> {
                    val node = LiteralType(LiteralValue.StringValue(currentTokenInterface.text))
                    opStack.add(node)
                }
                Identifier -> {
                    val node = VariableType(currentTokenInterface.text, null, false)
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
                else -> throw IllegalArgumentException(
                    "Unexpected token type: ${currentTokenInterface.type} at ${tokenInterfaces[index].position}"
                )
            }
        }
        return Pair(opStack.pop(), index)
    }

    private fun parseBinaryOperation(
        opStack: Stack<DynamicNode>,
        operation: (DynamicNode, DynamicNode) -> DynamicNode
    ): DynamicNode {
        require(opStack.size >= 2) { "Operation is missing arguments" }
        val rightNode = opStack.pop()
        val leftNode = opStack.pop()
        return operation(leftNode, rightNode)
    }

    private fun buildQueue(
        tokenInterfaces: List<TokenInterface>,
        startIndex: Int,
        endTokenType: TokenType
    ):
        Pair<Queue<TokenInterface>, Int> {
        var currentIndex = startIndex
        val stack: Stack<TokenInterface> = Stack()
        val queue: Queue<TokenInterface> = LinkedList()

        while (currentIndex < tokenInterfaces.size && tokenInterfaces[currentIndex].type != endTokenType) {
            val token = tokenInterfaces[currentIndex]
            handleToken(token, stack, queue, tokenInterfaces, currentIndex)
            currentIndex++
        }
        while (stack.isNotEmpty()) {
            require(queue.peek().type != OpenParenthesis) {
                "Parenthesis was opened and never closed at at ${tokenInterfaces[currentIndex].position}"
            }
            queue.add(stack.pop())
        }
        return Pair(queue, currentIndex)
    }

    private fun handleToken(
        tokenInterface: TokenInterface,
        stack: Stack<TokenInterface>,
        queue: Queue<TokenInterface>,
        tokenInterfaces: List<TokenInterface>,
        currentIndex: Int
    ) {
        when (tokenInterface.type) {
            NumberLiteral, StringLiteral, Identifier, CloseParenthesis -> {
                queue.add(tokenInterface)
            }
            CloseParenthesis -> {
                handleCloseParenthesis(stack, queue, tokenInterfaces, currentIndex)
            }
            Multiply, Divide -> {
                stack.add(tokenInterface)
            }
            Plus, Minus -> {
                handlePlusMinus(stack, queue, tokenInterface)
            }
            else -> throw IllegalArgumentException(
                "Unexpected token type in queue builder: " +
                    "${tokenInterface.type} at " +
                    "${tokenInterfaces[currentIndex].position}"
            )
        }
    }

    private fun handleCloseParenthesis(
        stack: Stack<TokenInterface>,
        queue: Queue<TokenInterface>,
        tokenInterfaces: List<TokenInterface>,
        currentIndex: Int
    ) {
        while (stack.isNotEmpty()) {
            val poppedToken = stack.pop()
            if (poppedToken.type == OpenParenthesis) {
                break
            }
            queue.add(poppedToken)
            require(stack.isNotEmpty()) {
                "Missing opening parenthesis for ')' at ${tokenInterfaces[currentIndex].position}"
            }
        }
    }

    private fun handlePlusMinus(
        stack: Stack<TokenInterface>,
        queue: Queue<TokenInterface>,
        tokenInterface: TokenInterface
    ) {
        if (stack.isEmpty() || ((stack.peek().type != Multiply) && (stack.peek().type != Divide))) {
            stack.add(tokenInterface)
        } else {
            queue.add(stack.pop())
            stack.add(tokenInterface)
        }
    }
}
