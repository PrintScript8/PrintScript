package elements

import node.dynamic.BooleanType
import node.dynamic.DivisionType
import node.dynamic.DynamicNode
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.MultiplyType
import node.dynamic.SubtractType
import node.dynamic.SumType
import node.dynamic.VariableType
import token.Bool
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

class RightSideParser(private val allowedTypes: Set<TokenType>) {

    fun parseRightHandSide(
        tokens: List<TokenInterface>,
        startIndex: Int,
        endTokenType: TokenType
    ): Pair<DynamicNode, Int> {
        val (expressionQueue, index) = buildQueue(tokens, startIndex, endTokenType)
        val opStack = Stack<DynamicNode>()
        require(expressionQueue.isNotEmpty()) { "Missing assignee in assignment! at ${tokens[index].position}" }
        processQueue(expressionQueue, opStack, tokens, index)
        return Pair(opStack.pop(), index)
    }

    private fun processQueue(
        expressionQueue: Queue<TokenInterface>,
        opStack: Stack<DynamicNode>,
        tokens: List<TokenInterface>,
        index: Int
    ) {
        while (expressionQueue.isNotEmpty()) {
            val currentToken = expressionQueue.remove()
            if (allowedTypes.contains(currentToken.type)) {
                handleToken(currentToken, opStack, tokens, index)
            } else {
                require(false) {
                    "Token type ${currentToken.type} is not supported at ${tokens[index - 1].position}"
                }
            }
        }
    }

    private fun handleToken(
        currentToken: TokenInterface,
        opStack: Stack<DynamicNode>,
        tokens: List<TokenInterface>,
        index: Int
    ) {
        when (currentToken.type) {
            NumberLiteral -> opStack.add(LiteralType(LiteralValue.NumberValue(currentToken.text.toDouble())))
            StringLiteral -> opStack.add(LiteralType(LiteralValue.StringValue(currentToken.text)))
            Bool -> opStack.add(BooleanType(LiteralValue.StringValue(currentToken.text)))
            Identifier -> opStack.add(VariableType(currentToken.text, null, false))
            Multiply -> opStack.add(parseBinaryOperation(opStack) { left, right -> MultiplyType(left, right, null) })
            Divide -> opStack.add(parseBinaryOperation(opStack) { left, right -> DivisionType(left, right, null) })
            Plus -> opStack.add(parseBinaryOperation(opStack) { left, right -> SumType(left, right, null) })
            Minus -> opStack.add(parseBinaryOperation(opStack) { left, right -> SubtractType(left, right, null) })
            else -> throw IllegalArgumentException(
                "Unexpected token type: ${currentToken.type} at ${tokens[index - 1].position}"

            )
        }
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

    private fun buildQueue(tokens: List<TokenInterface>, startIndex: Int, endTokenType: TokenType):
        Pair<Queue<TokenInterface>, Int> {
        var currentIndex = startIndex
        val stack = Stack<TokenInterface>()
        val queue = LinkedList<TokenInterface>()

        while (currentIndex < tokens.size && tokens[currentIndex].type != endTokenType) {
            val token = tokens[currentIndex]
            handleToken(token, stack, queue, tokens, currentIndex)
            currentIndex++
        }
        while (stack.isNotEmpty()) {
            require(queue.peek().type != OpenParenthesis) {
                "Parenthesis was opened and never closed at at ${tokens[currentIndex].position}"
            }
            queue.add(stack.pop())
        }
        return Pair(queue, currentIndex)
    }

    private fun handleToken(
        token: TokenInterface,
        stack: Stack<TokenInterface>,
        queue: Queue<TokenInterface>,
        tokens: List<TokenInterface>,
        currentIndex: Int
    ) {
        when (token.type) {
            NumberLiteral, StringLiteral, Identifier -> queue.add(token)
            OpenParenthesis -> stack.add(token)
            CloseParenthesis -> handleCloseParenthesis(stack, queue, tokens, currentIndex)
            Multiply, Divide -> stack.add(token)
            Plus, Minus -> handlePlusMinus(stack, queue, token)
            else -> queue.add(token)
        }
    }

    private fun handleCloseParenthesis(
        stack: Stack<TokenInterface>,
        queue: Queue<TokenInterface>,
        tokens: List<TokenInterface>,
        currentIndex: Int
    ) {
        while (stack.isNotEmpty()) {
            val poppedToken = stack.pop()
            if (poppedToken.type == OpenParenthesis) {
                break
            }
            queue.add(poppedToken)
            require(stack.isNotEmpty()) { "Missing opening parenthesis for ')' at ${tokens[currentIndex].position}" }
        }
    }

    private fun handlePlusMinus(
        stack: Stack<TokenInterface>,
        queue: Queue<TokenInterface>,
        token: TokenInterface
    ) {
        if (stack.isEmpty() || ((stack.peek().type != Multiply) && (stack.peek().type != Divide))) {
            stack.add(token)
        } else {
            queue.add(stack.pop())
            stack.add(token)
        }
    }
}
