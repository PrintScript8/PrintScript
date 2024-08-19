package parser.elements

import org.example.node.Node
import org.example.token.Token
import org.example.token.TokenType
import parser.strategies.DeclarationStrategy
import parser.strategies.ModifierStrategy
import parser.strategies.ParseStrategy

class TokenHandler {
    private val strategies: MutableMap<TokenType, ParseStrategy> = mutableMapOf()

    init {
        strategies[TokenType.MODIFIER] = ModifierStrategy()
        strategies[TokenType.IDENTIFIER_VAR] = DeclarationStrategy() // IMPORTANTE: Tambien funciona para llamadas a variable, no solo declarasion
        // Agrega más estrategias según sea necesario
    }

    fun handle(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        val tokenType = tokens[currentIndex].getType()
        val strategy = strategies[tokenType]
        return strategy?.parse(tokens, currentIndex, statementNodes) ?: (currentIndex + 1)
    }
}
