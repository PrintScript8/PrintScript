package lexer

import rule.TokenRule
import token.Position
import token.Token
import token.Whitespace
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class Lexer(private val rules: List<TokenRule>) : LexerInterface {

    override fun tokenize(input: InputStream): List<Token> {
        val reader = BufferedReader(InputStreamReader(input))
        val lines = reader.readLines()

        return tokenizeLines(lines, Position.initial())
    }

    private fun tokenizeLines(
        lines: List<String>,
        initialPosition: Position
    ): List<Token> {
        var position = initialPosition
        val tokens = mutableListOf<Token>()

        for (line in lines) {
            val (lineTokens, newPosition) = tokenizeLine(line, position)
            tokens.addAll(lineTokens)
            position = newPosition.copy(row = newPosition.row + 1, startColumn = 1, endColumn = 1)
        }

        return tokens
    }

    private fun tokenizeLine(
        line: String,
        initialPosition: Position
    ): Pair<List<Token>, Position> {
        var currentPosition = initialPosition
        var currentInput = line
        val tokens = mutableListOf<Token>()

        while (currentInput.isNotEmpty()) {
            val (token, newPosition, remainingInput) = matchToken(currentInput, currentPosition)
                ?: throw IllegalArgumentException(
                    "Unexpected character at row ${currentPosition.row}, column ${currentPosition.startColumn}"
                )

            if (token!!.type != Whitespace) {
                tokens.add(token)
            }

            currentPosition = newPosition
            currentInput = remainingInput
        }

        return tokens to currentPosition
    }

    private fun matchToken(
        input: String,
        position: Position
    ): Triple<Token?, Position, String>? {
        for (rule in rules) {
            val token = rule.match(input, position)
            if (token != null) {
                val tokenLength = token.text.length
                val newPosition = updatePosition(position, token.text, tokenLength)
                val remainingInput = input.drop(tokenLength)
                return Triple(token as Token, newPosition, remainingInput)
            }
        }
        return null
    }

    private fun updatePosition(
        position: Position,
        tokenText: String,
        tokenLength: Int
    ): Position {
        // Count new lines to calculate the row and column changes
        val newRow = position.row + tokenText.count { it == '\n' }

        // Calculate the position for the token
        val lastNewLineIndex = tokenText.lastIndexOf('\n')
        val newColumn = if (lastNewLineIndex >= 0) {
            tokenText.length - lastNewLineIndex - 1
        } else {
            position.startColumn + tokenLength
        }

        return Position(newRow, newColumn, newColumn)
    }
}
