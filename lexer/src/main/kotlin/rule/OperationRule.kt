package rule

import token.Divide
import token.Minus
import token.Multiply
import token.Plus
import token.Position
import token.Token
import token.TokenImpl
import token.TokenType

// Define una interfaz para operaciones
interface Operation {
    val symbol: String
    val tokenType: TokenType
}

// Implementa las operaciones básicas
object PlusOperation : Operation {
    override val symbol = "+"
    override val tokenType = Plus
}

object MinusOperation : Operation {
    override val symbol = "-"
    override val tokenType = Minus
}

object MultiplyOperation : Operation {
    override val symbol = "*"
    override val tokenType = Multiply
}

object DivideOperation : Operation {
    override val symbol = "/"
    override val tokenType = Divide
}

// Implementa la regla para operaciones
class OperationRule(private val operations: List<Operation>) : TokenRule {

    override fun match(input: String, position: Position): Token? {
        for (operation in operations) {
            if (input.startsWith(operation.symbol)) {
                return TokenImpl(
                    operation.tokenType,
                    operation.symbol,
                    Position(position.row, position.startColumn, position.startColumn)
                )
            }
        }
        return null
    }
}
