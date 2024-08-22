package lexer

import rule.TokenRule
import token.Position
import token.TokenImpl
import token.Whitespace

class LexerImpl(private val rules: List<TokenRule>) : Lexer {

    override fun tokenize(input: String): List<TokenImpl> {
        val tokens = mutableListOf<TokenImpl>()
        var position = Position(row = 1, startColumn = 1, endColumn = 1)
        var currentInput = input

        while (currentInput.isNotEmpty()) {
            val (token, newPosition, remainingInput) = matchToken(currentInput, position)
                ?: throw IllegalArgumentException(
                    "Unexpected character at row ${position.row}, column ${position.startColumn}"
                )

            if (token != null) {
                if (token.type != Whitespace) {
                    tokens.add(token)
                }
            }

            position = newPosition
            currentInput = remainingInput
        }

        return tokens
    }

    private fun matchToken(
        input: String,
        position: Position
    ): Triple<TokenImpl?, Position, String>? {
        for (rule in rules) {
            val token = rule.match(input, position)
            if (token != null) {
                val tokenLength = token.text.length
                val newPosition = updatePosition(position, token.text, tokenLength)
                val remainingInput = input.drop(tokenLength)
                return Triple(token as TokenImpl, newPosition, remainingInput)
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
        val newStartColumn = if (lastNewLineIndex >= 0) {
            tokenText.length - lastNewLineIndex
        } else {
            position.startColumn + tokenLength
        }
        return Position(newRow, newStartColumn, newStartColumn)
    }
}
