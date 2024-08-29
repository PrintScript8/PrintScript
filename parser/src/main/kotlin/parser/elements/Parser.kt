package parser.elements

import node.staticpkg.StaticNode
import token.Token

interface Parser {
    fun parse(tokens: List<Token>): List<StaticNode>
}
