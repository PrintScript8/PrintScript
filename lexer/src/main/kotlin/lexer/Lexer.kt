package lexer

import rule.TokenRule
import token.Position
import token.Whitespace

class Lexer(private val rules: List<TokenRule>) {

    fun tokenize(input: String): List<TokenImpl> {
        val tokens = mutableListOf<TokenImpl>()
        var position = Position(row = 1, startColumn = 1, endColumn = 1)
        var currentInput = input

        while (currentInput.isNotEmpty()) {
            var matched = false

            for (rule in rules) {
                val token = rule.match(currentInput, position)
                if (token != null) {
                    // Si el token no es Whitespace, lo agregamos
                    if (token.type != Whitespace) {
                        tokens.add(token as TokenImpl)
                    }
                    val tokenLength = token.text.length

                    // Actualizar posición
                    val newRow = position.row + token.text.count { it == '\n' }
                    val lastNewLineIndex = token.text.lastIndexOf('\n')
                    val newStartColumn = if (lastNewLineIndex >= 0) {
                        token.text.length - lastNewLineIndex
                    } else {
                        position.startColumn + tokenLength
                    }

                    position = Position(newRow, newStartColumn, newStartColumn)
                    currentInput = currentInput.drop(tokenLength)
                    matched = true
                    break
                }
            }

            if (!matched) {
                throw IllegalArgumentException("Unexpected character at row ${position.row}, column ${position.startColumn}")
            }
        }

        return tokens
    }
}
