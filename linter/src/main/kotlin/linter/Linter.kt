package linter

import error.Error
import node.staticpkg.StaticNode

interface Linter {
    fun lint(iterator: Iterator<StaticNode>): List<Error>
}
