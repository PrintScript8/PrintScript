package lexer

import rule.TokenRule
import token.Position
import token.TokenInterface
import token.Whitespace

class LexerImpl(private val rules: List<TokenRule>) : Lexer {

    override fun tokenize(input: String): List<TokenInterface> {
        val tokens = mutableListOf<TokenInterface>()
        var position = Position(row = 1, startColumn = 1, endColumn = 1)
        var currentInput = input

        while (currentInput.isNotEmpty()) {
            val (token, newPosition, remainingInput) = matchToken(currentInput, position)
                ?: throw IllegalArgumentException(
                    "Unexpected character at row ${position.row}, column ${position.startColumn}"
                )

            if (token!!.type != Whitespace) {
                tokens.add(token)
            }

            position = newPosition
            currentInput = remainingInput
        }
        return tokens
    }

    override fun iterator(input: String): Iterator<TokenInterface> {
        return object : Iterator<TokenInterface> {
            var position = Position(row = 1, startColumn = 1, endColumn = 1)
            var currentInput = input
            var nextToken: TokenInterface? = null

            override fun hasNext(): Boolean {
                if (nextToken == null && currentInput.isNotEmpty()) {
                    val (token, newPosition, remainingInput) = matchToken(currentInput, position)
                        ?: throw IllegalArgumentException(
                            "Unexpected character at row ${position.row}, column ${position.startColumn}"
                        )
                    if (token!!.type != Whitespace) {
                        nextToken = token
                        // Aca si es un whitespace quiero que lexee el proximo antes de salir de esta funcion
                        // De esta manera, el next token no va a ser null lo que rompe el hasNext
                    }
                    position = newPosition
                    currentInput = remainingInput
                    if(token.type == Whitespace) {
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
        position: Position
    ): Triple<TokenInterface?, Position, String>? {
        for (rule in rules) {
            val token = rule.match(input, position)
            if (token != null) {
                val tokenLength = token.text.length
                val newPosition = updatePosition(position, token.text, tokenLength)
                val remainingInput = input.drop(tokenLength)
                return Triple(token as TokenInterface, newPosition, remainingInput)
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
