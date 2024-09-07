package parser.elements

import node.staticpkg.StaticNode

interface ParserInterface {
    fun parse(): List<StaticNode>
    fun iterator(): Iterator<StaticNode>
}
