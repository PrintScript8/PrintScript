package org.example.parser

import node.staticpkg.*
import node.dynamic.*
import org.example.node.Node
import org.example.token.Token
import org.example.token.TokenType
import type.LiteralType
import type.LiteralValue

class ParserImpl : Parser {

    override fun parse(tokens: List<Token>): List<StaticNode> {
        var i = 0
        val astList = mutableListOf<StaticNode>()
        var statementNodes: MutableList<Node> = mutableListOf()

        while (i < tokens.size) {
            when (tokens[i].getType()) {
                TokenType.MODIFIER -> {
                    statementNodes.add(parseModifier(tokens, i))
                    i++
                }

                TokenType.IDENTIFIER_VAR -> {
                    if (statementNodes.isNotEmpty() && statementNodes.last() is ModifierType) {
                        // Si tenemos un modificador antes del identificador, es una declaración
                        statementNodes.add(parseDeclaration(tokens, i, statementNodes))
                    } else {
                        // Si no hay modificador, es una variable utilizada como valor
                        statementNodes.add(VariableType(tokens[i].getString(), LiteralValue.StringValue(""), false))
                    }
                    i++
                }

                TokenType.DECLARATION -> {
                    // Procesar declaración si es necesario
                    i++
                }

                TokenType.IDENTIFIER_TYPE -> {
                    // Este debería ser el tipo de la declaración, así que lo agregamos
                    i++
                }

                TokenType.ENDING -> {
                    if (statementNodes.isNotEmpty()) {
                        astList.add(statementNodes.first() as StaticNode)
                        statementNodes.clear()
                    }
                    i++
                }

                else -> i++
            }
        }
        return object : ArrayList<StaticNode>(astList) {
            override fun toString(): String {
                return this.joinToString(separator = "\n") { it.toString() }
            }
        }
    }

    private fun parseAssign(tokens: List<Token>, index: Int, statementNodes: List<Node>): StaticNode {
        val valueNode = parseValue(tokens, index + 1)
        return AssignationType(statementNodes.last() as DeclarationType, valueNode)
    }

    private fun parseValue(tokens: List<Token>, index: Int): DynamicNode {
        return when (tokens[index].getType()) {
            TokenType.NUMBER_LITERAL -> LiteralType(LiteralValue.NumberValue(tokens[index].getString().toDouble()))
            TokenType.STRING_LITERAL -> LiteralType(LiteralValue.StringValue(tokens[index].getString()))
            TokenType.OPERAND -> {
                val left = parseValue(tokens, index - 1)
                val right = parseValue(tokens, index + 1)
                SumType(left, right, null)
            }
            else -> throw IllegalArgumentException("Unexpected token type: ${tokens[index].getType()}")
        }
    }

    private fun parseModifier(tokens: List<Token>, index: Int): StaticNode {
        val tk: Token = tokens[index]
        return ModifierType(tk.getString(), tk.getString() != "const")
    }

    private fun parseDeclaration(tokens: List<Token>, index: Int, statementNodes: List<Node>): StaticNode {
        val lastNode: Node = statementNodes.last()
        if (lastNode !is ModifierType) {
            // If rdundante por ahora
            throw IllegalArgumentException("Expected lastNode to be of type ModifierType")
        }
        val identifierType = IdentifierType()
        return DeclarationType(lastNode as ModifierType, identifierType, tokens[index].getString())
    }

}
