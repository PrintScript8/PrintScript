package rule.operation

import rule.TokenRule
import token.Divide
import token.Minus
import token.Multiply
import token.Plus
import token.Position
import token.Token
import token.TokenInterface
import token.TokenType

interface Operation {
    val symbol: Char
    val tokenType: TokenType
}

object PlusOperation : Operation {
    override val symbol = '+'
    override val tokenType = Plus
}

object MinusOperation : Operation {
    override val symbol = '-'
    override val tokenType = Minus
}

object MultiplyOperation : Operation {
    override val symbol = '*'
    override val tokenType = Multiply
}

object DivideOperation : Operation {
    override val symbol = '/'
    override val tokenType = Divide
}

class OperationRule(private val operations: List<Operation>) : TokenRule {

    override fun match(input: String, currentIndex: Int, position: Position): TokenInterface? {
        for (operation in operations) {
            if (input[currentIndex] == operation.symbol) {
                return Token(
                    operation.tokenType,
                    (operation.symbol).toString(),
                    Position(position.row, position.startColumn, position.startColumn)
                )
            }
        }
        return null
    }
}
