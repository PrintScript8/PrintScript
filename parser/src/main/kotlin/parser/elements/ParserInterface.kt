package parser.elements

import node.staticpkg.StaticNode
import token.Token

interface ParserInterface {
    fun parse(): List<StaticNode>
    fun iterator(): Iterator<StaticNode>
}
