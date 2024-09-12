package parser

import node.staticpkg.StaticNode
import parser.elements.StatementParser
import parser.elements.TokenHandler
import token.CloseBrace
import token.Else
import token.Ending
import token.OpenBrace
import token.TokenInterface

class Parser(tokenHandler: TokenHandler, private val tokenIterator: Iterator<TokenInterface>) :
    ParserInterface {

    private val statementParser: StatementParser = StatementParser(tokenHandler)
    private var carriedToken: TokenInterface? = null

    // Este metodo parse ahora procesará un solo statement
    override fun parse(): StaticNode? {
        val statementTokens = mutableListOf<TokenInterface>()
        var currentToken: TokenInterface? = null

        if (carriedToken != null) {
            statementTokens.add(carriedToken!!)
            carriedToken = null
        }

        while (tokenIterator.hasNext()) {
            // hay que manejar que pasa cuando no hay next y el ultimo no fue un ; o }
            currentToken = tokenIterator.next()
            statementTokens.add(currentToken)
            val result = filterToken(currentToken, statementTokens)
            if (result != null) { return result }
        }
        if (currentToken != null) {
            require(statementTokens.isEmpty()) { "Missing ';' at end of statement: ${currentToken.position}" }
        }
        return null
    }

    private fun filterToken(currenToken: TokenInterface, statementTokens: MutableList<TokenInterface>): StaticNode? {
        return when (currenToken.type) {
            is OpenBrace -> {
                val blockNode = parseBlock(statementTokens)
                statementTokens.clear()
                blockNode
            }
            is Ending -> {
                val statementNodes = statementParser.parseStatement(statementTokens)
                statementTokens.clear()
                statementNodes.firstOrNull()
            }
            else -> null
        }
    }

    private fun parseBlock(statementTokens: MutableList<TokenInterface>): StaticNode {
        val blockTokens: MutableList<TokenInterface> = statementTokens
        var blockLevel = 1 // Empieza en 1 porque ya viste la apertura '{'
        var currentToken: TokenInterface = statementTokens.last()

        while (tokenIterator.hasNext()) {
            currentToken = tokenIterator.next()
            blockTokens.add(currentToken)

            if (currentToken.type == OpenBrace) {
                blockLevel++
            } else if (currentToken.type == CloseBrace) {
                blockLevel--
            }

            // Cuando el bloque se cierra completamente
            if (blockLevel == 0) {
                if (!tokenIterator.hasNext()) {
                    return statementParser.parseStatement(blockTokens).firstOrNull()!!
                }
                currentToken = tokenIterator.next()
                if (currentToken.type is Else) {
                    blockTokens.add(currentToken)
                } else {
                    carriedToken = currentToken
                    return statementParser.parseStatement(blockTokens).firstOrNull()!!
                }
            }
        }
        error("Block not closed properly at: ${currentToken.position}")
    }

    override fun iterator(): Iterator<StaticNode> {
        return object : Iterator<StaticNode> {
            private var nextNode: StaticNode? = null

            override fun hasNext(): Boolean {
                if (nextNode == null) {
                    nextNode = parse()
                }
                return nextNode != null
            }

            override fun next(): StaticNode {
                if (!hasNext()) throw NoSuchElementException()
                val currentNode = nextNode
                nextNode = null
                return currentNode!!
            }
        }
    }
}
