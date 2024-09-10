package lexer

import reader.ReaderInterface
import rule.TokenRule
import token.Position
import token.Token
import token.TokenInterface
import token.Whitespace

class Lexer(private val rules: List<TokenRule>, private val reader: ReaderInterface) : LexerInterface {

    override fun iterator(): Iterator<TokenInterface> {
        return object : Iterator<TokenInterface> {
            var position = Position.initial()
            var currentIndex = 0
            var nextToken: TokenInterface? = null
            var currentSentence: String = ""

            override fun hasNext(): Boolean {
                while (nextToken == null) {
                    if (currentIndex >= currentSentence.length) {
                        currentIndex = 0
                        currentSentence = reader.readUntil(';')
                    }

                    if (currentSentence.isNotEmpty()) {
                        processTokens()
                    } else if (!reader.hasNextChar()) {
                        return false
                    }
                }
                return true
            }

            override fun next(): TokenInterface {
                if (!hasNext()) throw NoSuchElementException()
                val token = nextToken
                nextToken = null
                return token!!
            }

            private fun processTokens() {
                while (currentIndex < currentSentence.length) {
                    val (newToken, newIndex, updatedPosition) = matchToken(currentSentence, currentIndex, position)
                    position = updatedPosition
                    currentIndex = newIndex

                    if (newToken != null) {
                        nextToken = newToken
                        return
                    }
                }
            }
        }
    }

    private fun matchToken(
        tokenText: String,
        currentIndex: Int,
        position: Position
    ): Triple<Token?, Int, Position> {
        require(currentIndex < tokenText.length) {
            "Index out of bounds: currentIndex ($currentIndex) " +
                "exceeds tokenText length (${tokenText.length})"
        }

        for (rule in rules) {
            val token = rule.match(tokenText, currentIndex, position)
            if (token != null) {
                val tokenLength = token.text.length
                val newPosition = updatePosition(position, token.text, tokenLength)

                // Si es un espacio en blanco, solo actualizamos posición
                if (token.type == Whitespace) {
                    return Triple(null, currentIndex + tokenLength, newPosition)
                }

                return Triple(token as Token, currentIndex + tokenLength, newPosition)
            }
        }
        throw IllegalArgumentException("Unexpected character at row ${position.row}, column ${position.startColumn}")
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
