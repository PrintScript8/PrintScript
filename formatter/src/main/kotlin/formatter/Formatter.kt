package formatter

import node.staticpkg.StaticNode

interface Formatter {
    fun execute(list: List<StaticNode>): String
}