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
import token.BooleanLiteral
import token.CloseParenthesis
import token.Divide
import token.Identifier
import token.Minus
import token.Multiply
import token.NumberLiteral
import token.OpenParenthesis
import token.Plus
import token.StringLiteral
import token.Token
import token.TokenType
import java.util.LinkedList
import java.util.Queue
import java.util.Stack

class RightSideParser(private val allowedTypes: Set<TokenType>) {

    fun parseRightHandSide(tokens: List<Token>, startIndex: Int, endTokenType: TokenType): Pair<DynamicNode, Int> {
        val tuple: Pair<Queue<Token>, Int> = buildQueue(tokens, startIndex, endTokenType)
        val expressionQueue: Queue<Token> = tuple.first
        val index: Int = tuple.second
        val opStack: Stack<DynamicNode> = Stack()
        require(expressionQueue.isNotEmpty()) { "Missing assignee in assignment! at ${tokens[index].position}" }

        while (expressionQueue.isNotEmpty()) {
            val currentToken: Token = expressionQueue.remove()
            if (allowedTypes.contains(currentToken.type)) {
                when (currentToken.type) {
                    NumberLiteral -> {
                        val node = LiteralType(LiteralValue.NumberValue(currentToken.text.toDouble()))
                        opStack.add(node)
                    }
                    StringLiteral -> {
                        val node = LiteralType(LiteralValue.StringValue(currentToken.text))
                        opStack.add(node)
                    }
                    BooleanLiteral -> {
                        val node = BooleanType(LiteralValue.StringValue(currentToken.text))
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
                    else -> throw IllegalArgumentException(
                        "Unexpected token type: ${currentToken.type} at ${tokens[index - 1].position}"
                    )
                }
            } else {
                require(false) {
                    "Token type ${currentToken.type} is not supported at ${tokens[index - 1].position}"
                }
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

    private fun buildQueue(tokens: List<Token>, startIndex: Int, endTokenType: TokenType): Pair<Queue<Token>, Int> {
        var currentIndex = startIndex
        val stack: Stack<Token> = Stack()
        val queue: Queue<Token> = LinkedList()

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
        token: Token,
        stack: Stack<Token>,
        queue: Queue<Token>,
        tokens: List<Token>,
        currentIndex: Int
    ) {
        when (token.type) {
            NumberLiteral, StringLiteral, Identifier -> {
                queue.add(token)
            }
            OpenParenthesis -> {
                stack.add(token)
            }
            CloseParenthesis -> {
                handleCloseParenthesis(stack, queue, tokens, currentIndex)
            }
            Multiply, Divide -> {
                stack.add(token)
            }
            Plus, Minus -> {
                handlePlusMinus(stack, queue, token)
            }
            else -> queue.add(token)
        }
    }

    private fun handleCloseParenthesis(
        stack: Stack<Token>,
        queue: Queue<Token>,
        tokens: List<Token>,
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
        stack: Stack<Token>,
        queue: Queue<Token>,
        token: Token
    ) {
        if (stack.isEmpty() || ((stack.peek().type != Multiply) && (stack.peek().type != Divide))) {
            stack.add(token)
        } else {
            queue.add(stack.pop())
            stack.add(token)
        }
    }
}
