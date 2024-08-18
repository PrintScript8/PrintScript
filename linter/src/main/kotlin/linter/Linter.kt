package linter

import node.staticpkg.StaticNode

interface Linter {
    fun lint(list: List<StaticNode>)
}