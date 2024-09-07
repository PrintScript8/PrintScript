package lexer

import rule.TokenRule
import token.Position
import token.Token
import token.Whitespace

class Lexer(private val rules: List<TokenRule>) : LexerInterface {

    override fun tokenize(input: String): List<Token> {
        return tokenizeLines(input.lines(), Position.initial())
    }

    private fun tokenizeLines(
        lines: List<String>,
        initialPosition: Position
    ): List<Token> {
        var position = initialPosition
        val tokens = mutableListOf<Token>()

        for (line in lines) {
            val lineTokens = tokenizeLine(line, position)
            tokens.addAll(lineTokens)
            position = Position(row = position.row + 1, startColumn = 1, endColumn = 1)
        }
        return tokens
    }

    private fun tokenizeLine(
        line: String,
        initialPosition: Position
    ): List<Token> {
        var currentPos = initialPosition
        var currentIndex = 0
        val tokens = mutableListOf<Token>()

        while (currentIndex < line.length) {
            val (token, newPosition, newIndex) = matchToken(line, currentPos, currentIndex)
                ?: throw IllegalArgumentException(
                    "Unexpected character at row ${currentPos.row}, column ${currentPos.startColumn}"
                )

            if (token!!.type != Whitespace) {
                tokens.add(token)
            }
            currentPos = newPosition
            currentIndex = newIndex
        }
        return tokens
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
            tokenText.length - lastNewLineIndex - 1
        } else {
            position.startColumn + tokenLength
        }
        return Position(newRow, newColumn, newColumn)
    }
}
