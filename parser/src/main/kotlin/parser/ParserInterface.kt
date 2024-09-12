package parser

import node.staticpkg.StaticNode

interface ParserInterface {
    fun parse(): StaticNode?
    fun iterator(): Iterator<StaticNode>
}
