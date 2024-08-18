package org.example.parser

import node.staticpkg.StaticNode
import org.example.token.Token

interface Parser {

    fun parse(tokens: List<Token>): List<StaticNode>
}