package parser.elements

import org.example.node.Node
import parser.strategies.*
import token.*

class TokenHandler {
    private val strategies: MutableMap<TokenType, ParseStrategy> = mutableMapOf()

    init {
        strategies[Modifier] = ModifierStrategy()
        strategies[Identifier] = DeclarationStrategy() // IMPORTANTE: Tambien funciona para llamadas a variable, no solo declarasion
        strategies[Assignment] = AssignationStrategy()
        strategies[NativeMethod] = MethodStrategy()
    }

    fun handle(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        val tokenType = tokens[currentIndex].type
        val strategy = strategies[tokenType]
        return strategy?.parse(tokens, currentIndex, statementNodes) ?: (currentIndex + 1)
    }
}
