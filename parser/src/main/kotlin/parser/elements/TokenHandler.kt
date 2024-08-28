package parser.elements

import node.Node
import parser.strategies.AssignationStrategy
import parser.strategies.DeclarationStrategy
import parser.strategies.MethodStrategy
import parser.strategies.ModifierStrategy
import parser.strategies.ParseStrategy
import token.Assignment
import token.Identifier
import token.Modifier
import token.NativeMethod
import token.Token
import token.TokenType

class TokenHandler {
    private val strategies: MutableMap<TokenType, ParseStrategy> = mutableMapOf()

    init {
        strategies[Modifier] = ModifierStrategy()
        // IMPORTANTE: Tambien funciona para llamadas a variable, no solo declarasion
        strategies[Identifier] = DeclarationStrategy()
        strategies[Assignment] = AssignationStrategy()
        strategies[NativeMethod] = MethodStrategy()
    }

    fun handle(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        val tokenType = tokens[currentIndex].type
        val strategy = strategies[tokenType]
        return strategy?.parse(tokens, currentIndex, statementNodes) ?: (currentIndex + 1)
    }
}
