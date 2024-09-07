package lexer

import rule.TokenRule
import token.Position
import token.Token
import token.TokenInterface
import token.Whitespace

class Lexer(private val rules: List<TokenRule>, private val input: String) : LexerInterface {

    override fun iterator(): Iterator<TokenInterface> {
        return object : Iterator<TokenInterface> {
            var position = Position.initial()
            var currentIndex = 0
            var nextToken: TokenInterface? = null

            override fun hasNext(): Boolean {
                if (nextToken == null && (input.substring(currentIndex).isNotEmpty())) {
                    val (token, newPosition, updatedIndex) = matchToken(input, position, currentIndex)
                        ?: throw IllegalArgumentException(
                            "Unexpected character at row ${position.row}, column ${position.startColumn}"
                        )
                    if (token!!.type != Whitespace) {
                        nextToken = token
                    }
                    position = newPosition
                    currentIndex = updatedIndex
                    if (token.type == Whitespace) {
                        hasNext()
                    }
                }
                return nextToken != null
            }

            override fun next(): TokenInterface {
                if (!hasNext()) throw NoSuchElementException()
                val token = nextToken
                nextToken = null
                return token!!
            }
        }
    }

    private fun matchToken(
        input: String,
        position: Position,
        currentIndex: Int
    ): Triple<Token?, Position, Int>? {
        val substring = input.substring(currentIndex)

        for (rule in rules) {
            val token = rule.match(substring, position)
            if (token != null) {
                val tokenLength = token.text.length
                val newPosition = updatePosition(position, token.text, tokenLength)
                val newIndex = currentIndex + tokenLength
                return Triple(token as Token, newPosition, newIndex)
            }
        }
        return null
    }

    private fun updatePosition(
        position: Position,
        tokenText: String,
        tokenLength: Int
    ): Position {
        val newRow = position.row + tokenText.count { it == '\n' }

        val lastNewLineIndex = tokenText.lastIndexOf('\n')
        val newColumn = if (lastNewLineIndex >= 0) {
            tokenText.length - lastNewLineIndex
        } else {
            position.startColumn + tokenLength
        }
        return Position(newRow, newColumn, newColumn)
    }
}
