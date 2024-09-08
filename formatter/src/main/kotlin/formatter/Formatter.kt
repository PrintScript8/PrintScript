package formatter

import node.staticpkg.StaticNode

interface Formatter {
    fun execute(iterator: Iterator<StaticNode>): String
}
